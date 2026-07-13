package com.ticketbooking.movietickes.reporistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketbooking.movietickes.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
