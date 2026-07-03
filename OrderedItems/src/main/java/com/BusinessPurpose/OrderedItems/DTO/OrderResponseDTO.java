package com.BusinessPurpose.OrderedItems.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.BusinessPurpose.OrderedItems.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private String orderNumber;
    private Long userId;
    private String username;
    private List<OrderItemResponseDTO> items;
    private Integer quntatiy;
    private BigDecimal totalAmount;
    private Order.OrderStatus status;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

