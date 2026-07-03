package com.BusinessPurpose.OrderedItems.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BusinessPurpose.OrderedItems.DTO.UserRequestDTO;
import com.BusinessPurpose.OrderedItems.DTO.UserResponseDTO;
import com.BusinessPurpose.OrderedItems.services.UserService;
import com.BusinessPurpose.OrderedItems.wrapper.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@RequestBody @Valid UserRequestDTO response){
		UserResponseDTO user = userService.createUser(response);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.Success("create the account successfully", user));
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers(){
		return ResponseEntity.ok(ApiResponse.Success("here all account users", userService.getAllUsers()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> getUser(@PathVariable Long id){
		return ResponseEntity.ok(ApiResponse.Success("here the deatiles", userService.findUserById(id)));
	}

}
