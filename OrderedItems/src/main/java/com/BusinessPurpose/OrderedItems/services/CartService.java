package com.BusinessPurpose.OrderedItems.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BusinessPurpose.OrderedItems.DTO.AddToCartRequestDTO;
import com.BusinessPurpose.OrderedItems.DTO.CartItemResponseDTO;
import com.BusinessPurpose.OrderedItems.DTO.CartResponseDTO;
import com.BusinessPurpose.OrderedItems.DTO.UpdateCartItemDTO;
import com.BusinessPurpose.OrderedItems.Repository.CartItemRepository;
import com.BusinessPurpose.OrderedItems.Repository.CartRepository;
import com.BusinessPurpose.OrderedItems.Repository.UserRepository;
import com.BusinessPurpose.OrderedItems.entity.Cart;
import com.BusinessPurpose.OrderedItems.entity.CartItem;
import com.BusinessPurpose.OrderedItems.entity.Product;
import com.BusinessPurpose.OrderedItems.entity.User;
import com.BusinessPurpose.OrderedItems.exceptions.InsufficientStockException;
import com.BusinessPurpose.OrderedItems.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final UserRepository userRepository;

    // Get or create an active cart for a user
   
    public Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserIdAndStatus(userId, Cart.cartStatus.ACTIVE)
                .orElseGet(() -> {

                    User user = userRepository.findById(userId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "User not found with id: " + userId));

                    Cart cart = Cart.builder()
                            .user(user)
                            .status(Cart.cartStatus.ACTIVE)
                            .build();

                    return cartRepository.save(cart);
                });
    }
   
    //Get cart response DTO for a user
     
    @Transactional(readOnly = true)
    public CartResponseDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserIdAndStatus(userId, Cart.cartStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("No active cart found for user: " + userId));
        return mapToDTO(cart);
    }

    //Add item to cart
    
    public CartResponseDTO addItemToCart(Long userId, AddToCartRequestDTO request) {
        Cart cart = getOrCreateCart(userId);
        Product product = productService.findProductById(request.getProductId());

        // Validate 

        validateStock(product, request.getQuantity());

        // Check if item already exists in cart
        
        Optional<CartItem> existingItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + request.getQuantity();
            validateStock(product, newQuantity);
            item.setQuantity(newQuantity);
            cartItemRepository.save(item);
         
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();
            cart.getCartItems().add(newItem);
            cartItemRepository.save(newItem);
           
        }

        cartRepository.save(cart);
        return mapToDTO(cart);
    }

    //Update cart item quantity
    
    public CartResponseDTO updateCartItem(Long userId, Long itemId, UpdateCartItemDTO request) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + itemId));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to user's cart");
        }

        validateStock(item.getProduct(), request.getQuantity());
        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);
        cartRepository.save(cart);
        
        return mapToDTO(cart);
    }

    //Remove item from cart
    
    public CartResponseDTO removeItemFromCart(Long userId, Long itemId) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + itemId));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to user's cart");
        }

        cart.getCartItems().remove(item);
        cartItemRepository.delete(item);
        cartRepository.save(cart);
        
        return mapToDTO(cart);
    }

    //Clear entire cart
    
    public CartResponseDTO clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);

        List<CartItem> items = List.copyOf(cart.getCartItems());

        cartItemRepository.deleteAll(items);

        cart.getCartItems().clear();

        cartRepository.save(cart);

        return mapToDTO(cart);
    }

    private void validateStock(Product product, int requestedQuantity) {
        if (product.getStockQuantity() < requestedQuantity) {
            throw new InsufficientStockException(
                    String.format("Insufficient stock for product '%s'. Available: %d, Requested: %d",
                            product.getName(), product.getStockQuantity(), requestedQuantity));
        }
    }

    CartResponseDTO mapToDTO(Cart cart) {
        List<CartItemResponseDTO> items = cart.getCartItems().stream()
                .map(item -> CartItemResponseDTO.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .productImgUrl(item.getProduct().getImageUrl())
                        .quantatity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subTotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return CartResponseDTO.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .userName(cart.getUser().getUserName())
                .items(items)
                .totalItems(cart.getTotalItems())
                .totalAmount(cart.getTotalAmount())
                .status(cart.getStatus())
                .upDateTime(cart.getUpdatedAt())
                .build();
    }
}
