package com.ticketbooking.movietickes.exceptions;

@SuppressWarnings("serial")
public class SeatAlreadyBookedException extends RuntimeException{
	
	public SeatAlreadyBookedException(String message) {
        super(message);
    }

}
