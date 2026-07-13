package com.ticketbooking.movietickes.exceptions;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String message) {
        super(message);
    }

}
