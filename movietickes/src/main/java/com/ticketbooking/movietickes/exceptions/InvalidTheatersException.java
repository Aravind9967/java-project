package com.ticketbooking.movietickes.exceptions;

@SuppressWarnings("serial")
public class InvalidTheatersException extends RuntimeException {
    public InvalidTheatersException(String message) {
        super(message);
    }
}
