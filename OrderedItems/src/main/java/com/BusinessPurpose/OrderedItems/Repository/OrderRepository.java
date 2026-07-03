package com.BusinessPurpose.OrderedItems.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BusinessPurpose.OrderedItems.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
	//List<Order> findByUserIdOrderByCreatedAtIncre(Long userId);
	Optional<Order> findByOrderNumber(String orderNumber);
	List<Order> findByStatus(Order.OrderStatus status);
	

}
