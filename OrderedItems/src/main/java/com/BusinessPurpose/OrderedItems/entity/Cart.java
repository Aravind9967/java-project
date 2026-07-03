package com.BusinessPurpose.OrderedItems.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id" , nullable = false , unique = true)
	private User user;

	@OneToMany(
	        mappedBy = "cart",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	)
	@Builder.Default
	private List<CartItem> cartItems = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@Builder.Default
	private cartStatus status = cartStatus.ACTIVE;
	
	@Column(name = "created_At" , updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_At")
	private LocalDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
	
    public  BigDecimal getToBigDecimal() {
    	BigDecimal total = BigDecimal.ZERO;
    	
    	for(CartItem item : cartItems) {
    		total = total.add(item.getSubtotal());
    	}
    	
    	return total;
    }
    
    public int getTotalItems() {
    	int total = 0;
    	
    	 for (CartItem item : cartItems) {
    	        total += item.getQuantity();
    	    }

    	    return total;
    }
    
    public BigDecimal getTotalAmount() {
        if (cartItems == null || cartItems.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return cartItems.stream()
                .map(item -> item.getUnitPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public enum cartStatus {
    	 ACTIVE, CHECKED_OUT, ABANDONED
    }

}
