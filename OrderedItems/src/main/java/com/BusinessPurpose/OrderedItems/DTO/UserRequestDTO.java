package com.BusinessPurpose.OrderedItems.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {
	@NotBlank
	private String userName;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String fullName;
	@NotBlank
	private String phoneNumber;

}
