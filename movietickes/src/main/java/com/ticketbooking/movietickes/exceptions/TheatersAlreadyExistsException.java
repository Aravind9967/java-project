package com.ticketbooking.movietickes.exceptions;

@SuppressWarnings("serial")
public class TheatersAlreadyExistsException extends RuntimeException{
	public TheatersAlreadyExistsException(String message) {
        super(message);
    }

}
