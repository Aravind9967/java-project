package com.BusinessPurpose.OrderedItems.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.BusinessPurpose.OrderedItems.DTO.UserRequestDTO;
import com.BusinessPurpose.OrderedItems.DTO.UserResponseDTO;
import com.BusinessPurpose.OrderedItems.Repository.UserRepository;
import com.BusinessPurpose.OrderedItems.entity.User;
import com.BusinessPurpose.OrderedItems.exceptions.ResourceNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserResponseDTO createUser(UserRequestDTO request) {
		
		if (userRepository.existsByUserName(request.getUserName())) {
		    throw new IllegalArgumentException("Username already exists");
		}

		userRepository.findByEmail(request.getEmail())
		              .ifPresent(user -> {
		                  throw new IllegalArgumentException("Email already exists");
		              });
	   
	   User user = User.builder()
			           .userName(request.getUserName())
			           .email(request.getEmail())
			           .phoneNumber(request.getPhoneNumber())
			           .fullName(request.getFullName())
			           .build();
	   return mapToDto(userRepository.save(user));
	
		
	}
	  private UserResponseDTO mapToDto(User user) {
	        return UserResponseDTO.builder()
	        		.id(user.getId())
	                .userName(user.getUserName())
	                .email(user.getEmail())
	                .fullName(user.getFullName())
	                .phoneNumber(user.getPhoneNumber())
	                .createdAt(user.getCreatedAt())
	                .build();
	    }

	  @Transactional(readOnly = true)
	  public List<UserResponseDTO> getAllUsers(){
		  return userRepository.findAll().stream()
				  .map(this::mapToDto)
				  .collect(Collectors.toList());
	  }
	  
	  @Transactional(readOnly = true)
	  public UserResponseDTO findUserById(Long id) {

	      User user = userRepository.findById(id)
	    		  .orElseThrow(() -> new ResourceNotFoundException(
	    			        "User not found with id: " + id));
	      return mapToDto(user);
	  }
	  
	  

}
