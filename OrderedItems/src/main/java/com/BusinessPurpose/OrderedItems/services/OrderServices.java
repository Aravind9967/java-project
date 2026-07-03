package com.BusinessPurpose.OrderedItems.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BusinessPurpose.OrderedItems.DTO.CheckoutRequestDTO;
import com.BusinessPurpose.OrderedItems.DTO.OrderItemResponseDTO;
import com.BusinessPurpose.OrderedItems.DTO.OrderResponseDTO;
import com.BusinessPurpose.OrderedItems.Repository.CartRepository;
import com.BusinessPurpose.OrderedItems.Repository.OrderRepository;
import com.BusinessPurpose.OrderedItems.Repository.ProductRepository;
import com.BusinessPurpose.OrderedItems.entity.Cart;
import com.BusinessPurpose.OrderedItems.entity.Order;
import com.BusinessPurpose.OrderedItems.entity.OrderItem;
import com.BusinessPurpose.OrderedItems.entity.Product;
import com.BusinessPurpose.OrderedItems.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServices {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductRepository productRepository;

    // Checkout: convert cart to order, reduce stock, mark cart as checked out
 
    public OrderResponseDTO checkout(Long userId, CheckoutRequestDTO request) {
        Cart cart = cartRepository.findByUserIdAndStatus(userId, Cart.cartStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("No active cart found for user: " + userId));

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cannot checkout an empty cart");
        }

        // Build order items and reduce stock
        
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    int newStock = product.getStockQuantity() - cartItem.getQuantity();
                    if (newStock < 0) {
                        throw new RuntimeException("Insufficient stock for: " + product.getName());
                    }
                    product.setStockQuantity(newStock);
                    productRepository.save(product);

                    BigDecimal subtotal = cartItem.getUnitPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

                    return OrderItem.builder()
                            .product(product)
                            .quantity(cartItem.getQuantity())
                            .unitPrice(cartItem.getUnitPrice())
                            .subtotal(subtotal)
                            .build();
                })
                .collect(Collectors.toList());

        // Create order
        
        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .user(cart.getUser())
                .totalAmount(cart.getTotalAmount())
                .shippingAddress(request.getShippingAddress())
                .status(Order.OrderStatus.CONFIRMED)
                .build();

        order = orderRepository.save(order);

        // Link order items to order
        
        final Order savedOrder = order;
        orderItems.forEach(item -> item.setOrder(savedOrder));
        savedOrder.setOrderItems(orderItems);
        orderRepository.save(savedOrder);

        // Mark cart as checked out
        cart.setStatus(Cart.cartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        return mapToDTO(savedOrder);
    }

    // Get all orders for a user
     
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        userService.findUserById(userId); // validate user exists
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //Get a specific order
    
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
        return mapToDTO(order);
    }

    /**
     * Update order status
     */
    public OrderResponseDTO updateOrderStatus(Long orderId, Order.OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
        order.setStatus(newStatus);
        
        return mapToDTO(orderRepository.save(order));
    }

    /**
     * Cancel order and restore stock
     */
    public OrderResponseDTO cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

        if (order.getStatus() == Order.OrderStatus.SHIPPED || 
            order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new IllegalArgumentException("Cannot cancel order that is already " + order.getStatus());
        }

        // Restore stock
        order.getOrderItems().forEach(item -> {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        });

        order.setStatus(Order.OrderStatus.CANCELLED);
        return mapToDTO(orderRepository.save(order));
    }

    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "ORD-" + timestamp + "-" + (int)(Math.random() * 1000);
    }

    private OrderResponseDTO mapToDTO(Order order) {
        List<OrderItemResponseDTO> items = order.getOrderItems().stream()
                .map(item -> OrderItemResponseDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quntatiy(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subTotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUser().getId())
                .username(order.getUser().getUserName())
                .items(items)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .shippingAddress(order.getShippingAddress())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
