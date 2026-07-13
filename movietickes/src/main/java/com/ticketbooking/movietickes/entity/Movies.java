package com.ticketbooking.movietickes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(
	    name = "movies",
	    uniqueConstraints = {
	        @UniqueConstraint(columnNames = "title")
	    }
	)
public class Movies {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    
	public Movies() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getDuractionMinutes() {
		return duractionMinutes;
	}
	public void setDuractionMinutes(int duractionMinutes) {
		this.duractionMinutes = duractionMinutes;
	}
	private String title;
	private String genre;
	private int duractionMinutes;

}
