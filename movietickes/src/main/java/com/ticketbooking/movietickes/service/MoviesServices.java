package com.ticketbooking.movietickes.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketbooking.movietickes.entity.Movies;
import com.ticketbooking.movietickes.exceptions.BadRequestException;
import com.ticketbooking.movietickes.exceptions.ResourceNotFoundException;
import com.ticketbooking.movietickes.reporistory.MoviesRespository;

@Service
public class MoviesServices {
	@Autowired
	private final MoviesRespository moviesRespository;
	
	public MoviesServices(MoviesRespository moviesRespository) {
		this.moviesRespository = moviesRespository;
	}
	
	public List<Movies> getAllMovies(){
		    return moviesRespository.findAll();
	}

	public Movies getMoviesById(Long id) {
		return moviesRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("not found the id" +id));
	}
	
	public Movies createMovies(Movies movies) throws BadRequestException {

	    // 1. Null check
	    if (movies == null || movies.getTitle() == null) {
	        throw new BadRequestException("Invalid movie data");
	    }

	    // 2. Normalize title
	    String title = movies.getTitle();

	    if (title.isEmpty()) {
	        throw new BadRequestException("Title cannot be empty");
	    }

	    movies.setTitle(title);

	    // 3. Duration validation
	    if (movies.getDuractionMinutes() <= 0) {
	        throw new BadRequestException("Invalid duration");
	    }

	    // 4. Duplicate check (BEFORE save)
	    boolean exists = moviesRespository.findAll().stream()
                .anyMatch(m -> m.getTitle().equalsIgnoreCase(title));
        
        if (exists) {
            throw new BadRequestException("Movie with this title already exists");
        }

	    // 5. Save
	    return moviesRespository.save(movies);
	}

	
	public void deleteMovies(Long id) {
		Movies movies = getMoviesById(id);
		moviesRespository.delete(movies);
		
	}
	
	public Movies updateMovies( Long id, Movies moviesDetailes) {
		Movies movies = getMoviesById(id);
		movies.setTitle(moviesDetailes.getTitle());
		movies.setGenre(moviesDetailes.getGenre());
		movies.setDuractionMinutes(moviesDetailes.getDuractionMinutes());
		return moviesRespository.save(movies);
		
	}
	
	public List<Movies> searchByTitle(String title) {
        return moviesRespository.findByTitleContainingIgnoreCase(title);
    }
	

}
