package com.BusinessPurpose.OrderedItems.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.BusinessPurpose.OrderedItems.DTO.ProductResponseDTO;
import com.BusinessPurpose.OrderedItems.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByCategory(String category);
	List<Product> findByNameContainingIgnoreCase(String name);
	List<Product> findByStockQuantityGreaterThan(int quantity);
	
	@Query("SELECT DISTINCT p.category FROM Product p ORDER BY p.category")
	List<ProductResponseDTO> findAllCategories();

}
