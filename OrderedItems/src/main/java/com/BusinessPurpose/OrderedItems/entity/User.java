package com.BusinessPurpose.OrderedItems.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message =  "user name is entered mandetary")
	@Column(nullable = false , unique = true)
	private String userName;
	
	@NotBlank(message = "enter email is required")
	@Email(message = "invalied email id formate")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "fullName is required...")
	@Column(nullable = false)
	private String fullName;
	
	@NotBlank(message = "phone no is required")
	@Column(name = "phone_no" , nullable = false)
	private String phoneNumber;
	
	@Column(name = "created_At" , updatable = false)
	private LocalDateTime createdAt;
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}


}
