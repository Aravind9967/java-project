package com.BusinessPurpose.OrderedItems.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.BusinessPurpose.OrderedItems.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDTO {
	private Long id;
	private Long userId;
	private String userName;
	private List<CartItemResponseDTO> items;
	private int totalItems;
	private BigDecimal totalAmount;
	private Cart.cartStatus status;
	private LocalDateTime upDateTime;
	

}
