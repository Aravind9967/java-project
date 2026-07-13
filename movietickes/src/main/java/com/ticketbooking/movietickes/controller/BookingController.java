package com.ticketbooking.movietickes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ticketbooking.movietickes.dto.BookingRequest;
import com.ticketbooking.movietickes.entity.Booking;
import com.ticketbooking.movietickes.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest request) {

        Booking booking = bookingService.createBooking(
                request.getShowId(),
                request.getSeatNumbers(),
                request.getCustomerName(),
                request.getCustomerEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

}