package com.ticketbooking.movietickes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ticketbooking.movietickes.entity.Seat;
import com.ticketbooking.movietickes.entity.Show;
import com.ticketbooking.movietickes.exceptions.BadRequestException;
import com.ticketbooking.movietickes.reporistory.SeatRepository;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getAvailableSeats(Long showId) {
        return seatRepository.findByShowIdAndBookedFalse(showId);
    }

    public List<Seat> getSeatsForBooking(Long showId, List<String> seatNumbers) {

        List<Seat> seats = seatRepository
                .findByShowIdAndSeatNumberIn(showId, seatNumbers);

        if (seats.size() != seatNumbers.size()) {
            throw new BadRequestException("Invalid seat numbers.");
        }

        for (Seat seat : seats) {
            if (seat.isBooked()) {
                throw new BadRequestException(
                        "Seat already booked : " + seat.getSeatNumber());
            }
        }

        return seats;
    }

    public void bookSeats(List<Seat> seats) {

        for (Seat seat : seats) {
            seat.setBooked(true);
        }

        seatRepository.saveAll(seats);
    }

}