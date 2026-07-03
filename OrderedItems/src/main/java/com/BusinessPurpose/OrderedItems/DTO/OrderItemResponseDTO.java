package com.BusinessPurpose.OrderedItems.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {
	private Long productId;
	private String productName;
	private Integer quntatiy;
	private BigDecimal unitPrice;
	private BigDecimal subTotal;

}
