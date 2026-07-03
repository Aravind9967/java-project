package com.BusinessPurpose.OrderedItems.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BusinessPurpose.OrderedItems.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	
	Optional<Cart> findByUserId(Long UserId);
	Optional<Cart> findByUserIdAndStatus(Long userId, Cart.cartStatus status);

}
