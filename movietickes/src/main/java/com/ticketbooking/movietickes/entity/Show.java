package com.ticketbooking.movietickes.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "movie_show")
public class Show {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Movies movies;
    @ManyToOne
	private Theaters theaters;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Movies getMovies() {
		return movies;
	}
	public void setMovies(Movies movies) {
		this.movies = movies;
	}
	public Theaters getTheaters() {
		return theaters;
	}
	public void setTheaters(Theaters theaters) {
		this.theaters = theaters;
	}
	public LocalDateTime getShowTime() {
		return showTime;
	}
	public void setShowTime(LocalDateTime showTime) {
		this.showTime = showTime;
	}
	private LocalDateTime showTime;
	private double pricePerSeat;
	public double getPricePerSeat() {
		return pricePerSeat;
	}
	public void setPricePerSeat(double pricePerSeat) {
		this.pricePerSeat = pricePerSeat;
	}

}
