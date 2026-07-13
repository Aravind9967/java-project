package com.ticketbooking.movietickes.reporistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ticketbooking.movietickes.entity.Theaters;

public interface TheatersRespository extends JpaRepository<Theaters, Long> {

	boolean existsByNameAndCity(String name, String city);
	  Optional<Theaters> findByNameIgnoreCaseAndCityIgnoreCase(String name, String city);
	    
	    // Find all theaters in a city
	    List<Theaters> findByCityIgnoreCase(String city);
	    
	    // Find theaters by name
	    List<Theaters> findByNameContainingIgnoreCase(String name);

}