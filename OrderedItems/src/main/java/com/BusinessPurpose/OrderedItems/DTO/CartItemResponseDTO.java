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
public class CartItemResponseDTO {
	
	private Long id;
	private Long productId;
	private String productName;
	private String productImgUrl;
	private Integer quantatity;
	private BigDecimal unitPrice;
	private BigDecimal subTotal;
	

}
