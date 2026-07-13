package com.ticketbooking.movietickes.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketbooking.movietickes.entity.Movies;
import com.ticketbooking.movietickes.entity.Seat;
import com.ticketbooking.movietickes.entity.Show;
import com.ticketbooking.movietickes.entity.Theaters;
import com.ticketbooking.movietickes.exceptions.ResourceNotFoundException;
import com.ticketbooking.movietickes.reporistory.BookingRepository;
import com.ticketbooking.movietickes.reporistory.MoviesRespository;
import com.ticketbooking.movietickes.reporistory.SeatRepository;
import com.ticketbooking.movietickes.reporistory.ShowRespository;
import com.ticketbooking.movietickes.reporistory.TheatersRespository;

import jakarta.transaction.Transactional;

@Service
public class ShowService {
	@Autowired
	private final ShowRespository showRespository;
	private final SeatRepository seatRepository;
	private final MoviesRespository moviesRespository;
	private final TheatersRespository theatersRespository;
	
	public ShowService(ShowRespository showRespository,
			           SeatRepository seatRepository,
			           BookingRepository bookingRepository,
			           MoviesRespository moviesRespository,
			           TheatersRespository theatersRespository){
		this.showRespository = showRespository;
		this.seatRepository = seatRepository;
		this.moviesRespository = moviesRespository;
		this.theatersRespository = theatersRespository;
				
	}
	
	public List<Show> getAllShow(){
		return showRespository.findAll();
		
	}
	public Show getfindById(Long id) {
		return showRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("show is not found by .. id" + id));
	}
	@Transactional
	public Show createShow(Show show) {
		Movies movies = moviesRespository.findById(show.getMovies().getId())
				.orElseThrow(() -> new ResourceNotFoundException("movies is not found"));
		Theaters theaters = theatersRespository.findById(show.getTheaters().getId())
			    .orElseThrow(() -> new ResourceNotFoundException("theater is not found"));
		    show.setMovies(movies);
		    show.setTheaters(theaters);
		    
		    Show savedShow = showRespository.save(show);

	         generateSeatsForShow(savedShow, theaters.getTotalSeats());

		    return savedShow;
	}

	private void generateSeatsForShow(Show show, int totalSeats) {
	    int seatPerRow = 10;

	    List<Seat> seats = IntStream.range(0, totalSeats)
	        .mapToObj(i -> {
	            char row = (char) ('A' + (i / seatPerRow));
	            int col = (i % seatPerRow) + 1;

	            Seat seat = new Seat();
	            seat.setShow(show);
	            seat.setSeatNumber(row + String.valueOf(col));
	            seat.setBooked(false);
	            return seat;
	        })
	        .collect(Collectors.toList());

	    seatRepository.saveAll(seats);
	}

}
