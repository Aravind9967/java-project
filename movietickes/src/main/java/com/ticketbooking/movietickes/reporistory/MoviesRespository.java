package com.ticketbooking.movietickes.reporistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketbooking.movietickes.entity.Movies;

public interface MoviesRespository extends JpaRepository<Movies, Long>{

	List<Movies> searchMoviesByTitle(String title);

	List<Movies> searchByTitle(String tittle);

	List<Movies> findByTitleContainingIgnoreCase(String title);

	boolean existsByTitleIgnoreCase(String title);

}
