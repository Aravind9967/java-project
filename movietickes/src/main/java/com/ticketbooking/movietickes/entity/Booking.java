package com.ticketbooking.movietickes.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Entity
@Data
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String customerName;
	private String customerEmail;
	@ManyToOne
	private Show show;
	@ElementCollection
	private List<String> bookedSeats = new ArrayList<String>();
	private double totalAmount;
	private LocalDateTime bookedTime = LocalDateTime.now();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public Show getShow() {
		return show;
	}
	public void setShow(Show show) {
		this.show = show;
	}
	public List<String> getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(List<String> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public LocalDateTime getBookedTime() {
		return bookedTime;
	}
	public void setBookedTime(LocalDateTime bookedTime) {
		this.bookedTime = bookedTime;
	}

}
