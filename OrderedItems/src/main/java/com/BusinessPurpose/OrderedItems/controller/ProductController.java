package com.BusinessPurpose.OrderedItems.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BusinessPurpose.OrderedItems.DTO.ProductRequestDTO;
import com.BusinessPurpose.OrderedItems.DTO.ProductResponseDTO;
import com.BusinessPurpose.OrderedItems.services.ProductService;
import com.BusinessPurpose.OrderedItems.wrapper.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
	
	private final ProductService productService;
	@PostMapping
	public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(@RequestBody @Valid ProductRequestDTO request){
		ProductResponseDTO product = productService.createProduct(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.Success("product successfully created", product));
	}
	@GetMapping("/allproducts")
	public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProduct(){
		return ResponseEntity.ok(ApiResponse.Success("this is all products", productService.getAllProduct()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductResponseDTO>> getProduct(@PathVariable Long id){
		return ResponseEntity.ok(ApiResponse.Success("this is get by id.. ", productService.getProductById(id)));
	}
	
	@GetMapping("/category/{category}")
	public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getByCategory(@PathVariable String category){
		return ResponseEntity.ok(ApiResponse.Success("this is your result", productService.getProductByCategory(category)));
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> search(@RequestParam String search){
		return ResponseEntity.ok(ApiResponse.Success("this is the result", productService.searchProduct(search)));
	}
	
	@GetMapping("/categories")
	public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getCategories(){
		return ResponseEntity.ok(ApiResponse.Success("this is all categoris", productService.getAllCategories()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id){
		productService.deleteProduct(id);
		return ResponseEntity.ok(ApiResponse.Success("deleted successfully", null));
	}
	 @PutMapping("/{id}")
	    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(
	            @PathVariable Long id,
	            @Valid @RequestBody ProductRequestDTO request) {
	        return ResponseEntity.ok(ApiResponse.Success("Product updated", productService.updateProduct(id, request)));
	    }


	
	

}
