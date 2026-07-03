package com.BusinessPurpose.OrderedItems.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.BusinessPurpose.OrderedItems.DTO.ProductRequestDTO;
import com.BusinessPurpose.OrderedItems.DTO.ProductResponseDTO;
import com.BusinessPurpose.OrderedItems.Repository.ProductRepository;
import com.BusinessPurpose.OrderedItems.entity.Product;
import com.BusinessPurpose.OrderedItems.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public ProductResponseDTO createProduct(ProductRequestDTO request){
		Product product = Product.builder()
		        .name(request.getName())
		        .description(request.getDescription())
		        .price(request.getPrice())
		        .category(request.getCategory())
		        .imageUrl(request.getImageUrl())
		        .createdAt(request.getCreatedAt())
		        .stockQuantity(request.getStockQuantity())
		        .build();
		return mapToDTO(productRepository.save(product));
	}
	
	private ProductResponseDTO mapToDTO(Product product) {
		return ProductResponseDTO.builder()
				    .id(product.getId())
				    .name(product.getName())
				    .description(product.getDescription())
				    .price(product.getPrice())
				    .category(product.getCategory())
				    .stockQuantity(product.getStockQuantity())
				    .imageUrl(product.getImageUrl())
				    .createdAt(product.getCreatedAt())
				    .updatedAt(product.getUpdatedAt())
				    .build();
	}
	
	public List<ProductResponseDTO> getAllProduct(){
		return productRepository.findAll().stream()
				.map(this::mapToDTO)
				.collect(Collectors.toList());
	}
	Product findProductById(Long id) {
	    return productRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Product not found with id: " + id));
	}
	
	 public ProductResponseDTO getProductById(Long id) {
		 Product product = productRepository.findById(id)
	    		  .orElseThrow(() -> new ResourceNotFoundException(
	    			        "User not found with id: " + id));
	      return mapToDTO(product);
	  }
		public List<ProductResponseDTO> getProductByCategory(String category){
			return productRepository.findByCategory(category).stream()
					.map(this::mapToDTO)
					.collect(Collectors.toList());
		}
		@Transactional(readOnly = true)
		public List<ProductResponseDTO> searchProduct(String keyword) {

		    List<Product> product = productRepository.findByNameContainingIgnoreCase(keyword);

		    if (product.isEmpty()) {
		        throw new ResourceNotFoundException(
		                "Product is not available with keyword: " + keyword);
		    }

		    return product.stream()
		            .map(this::mapToDTO)
		            .toList();
		}
		
		@Transactional(readOnly = true)
		public List<ProductResponseDTO> getAllCategories(){
			return productRepository.findAllCategories();
		}
		
		public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
			Product product = findProductById(id);
			product.setName(request.getName());
			product.setDescription(request.getDescription());
			product.setCategory(request.getCategory());
			product.setPrice(request.getPrice());
			product.setStockQuantity(request.getStockQuantity());
			product.setImageUrl(request.getImageUrl());
			return mapToDTO(productRepository.save(product));
		}
		
		@Transactional
		public void deleteProduct(Long id) {

		    if (!productRepository.existsById(id)) {
		        throw new ResourceNotFoundException(
		                "Product not found with id: " + id);
		    }

		    productRepository.deleteById(id);
		}
	

	 
}
