package com.BusinessPurpose.OrderedItems.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Product name is required")
	private String name;
	
	private String description;
	
	@NotBlank(message = "Price is required")
	@Positive
	private BigDecimal price;
	
	@PositiveOrZero
	private Integer stockQuantity;
	
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
	
	@NotBlank
	private String category;
	private String imageUrl;
	

}


