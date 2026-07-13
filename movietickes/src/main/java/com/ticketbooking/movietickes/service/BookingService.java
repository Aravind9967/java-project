package com.ticketbooking.movietickes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ticketbooking.movietickes.entity.Booking;
import com.ticketbooking.movietickes.entity.Seat;
import com.ticketbooking.movietickes.entity.Show;
import com.ticketbooking.movietickes.exceptions.ResourceNotFoundException;
import com.ticketbooking.movietickes.exceptions.SeatAlreadyBookedException;
import com.ticketbooking.movietickes.reporistory.BookingRepository;
import com.ticketbooking.movietickes.reporistory.SeatRepository;
import com.ticketbooking.movietickes.reporistory.ShowRespository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

    private final ShowRespository showRespository;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    public BookingService(ShowRespository showRespository,
                          SeatRepository seatRepository,
                          BookingRepository bookingRepository) {

        this.showRespository = showRespository;
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Booking createBooking(Long showId,
                                 List<String> requestedSeats,
                                 String customerName,
                                 String customerEmail) {

        Show show = showRespository.findById(showId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Show not found with id : " + showId));

        List<Seat> availableSeats = seatRepository.findByShowIdAndBookedFalse(showId);

        List<Seat> seatsToBook = new ArrayList<>();

        for (String seatNumber : requestedSeats) {

            Seat seat = availableSeats.stream()
                    .filter(s -> s.getSeatNumber().equalsIgnoreCase(seatNumber))
                    .findFirst()
                    .orElseThrow(() ->
                            new SeatAlreadyBookedException(
                                    "Seat " + seatNumber + " is already booked or does not exist"));

            seat.setBooked(true);
            seatsToBook.add(seat);
        }

        seatRepository.saveAll(seatsToBook);

        Booking booking = new Booking();
        booking.setShow(show);
        booking.setCustomerName(customerName);
        booking.setCustomerEmail(customerEmail);
        booking.setBookedSeats(requestedSeats);
        booking.setTotalAmount(requestedSeats.size() * show.getPricePerSeat());

        return bookingRepository.save(booking);
    }
}