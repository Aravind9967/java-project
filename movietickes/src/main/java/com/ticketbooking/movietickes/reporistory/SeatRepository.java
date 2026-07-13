package com.ticketbooking.movietickes.reporistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketbooking.movietickes.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByShowIdAndBookedFalse(Long showId);

    List<Seat> findByShowIdAndSeatNumberIn(Long showId,
                                           List<String> seatNumbers);
}