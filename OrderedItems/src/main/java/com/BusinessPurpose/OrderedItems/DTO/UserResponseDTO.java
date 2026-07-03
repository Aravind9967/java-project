package com.BusinessPurpose.OrderedItems.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
	
	private Long id;
	private String userName;
	private String email;
	private String phoneNumber;
	private LocalDateTime createdAt;
	public String fullName;

}
