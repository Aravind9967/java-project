package com.ticketbooking.movietickes.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketbooking.movietickes.entity.Movies;
import com.ticketbooking.movietickes.exceptions.BadRequestException;
import com.ticketbooking.movietickes.exceptions.ResourceNotFoundException;
import com.ticketbooking.movietickes.reporistory.MoviesRespository;
import com.ticketbooking.movietickes.service.MoviesServices;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	@Autowired
	private final MoviesRespository moviesRespository;
	@Autowired
	private final MoviesServices moviesServices;
	@Autowired
	private MoviesRespository moviesService;
	
	public MovieController(MoviesRespository moviesRespository, MoviesServices moviesServices) {
		this.moviesRespository = moviesRespository;
		this.moviesServices = moviesServices;
	}
	
	@GetMapping
	public List<Movies> getAllMovies(){
		return moviesRespository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Movies> getMoviesById(@PathVariable Long id){
		Movies movies = moviesRespository.findById(id)
				         .orElseThrow(() -> new ResourceNotFoundException("Movies not found by id..." + id));
		return ResponseEntity.ok(movies);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> createMovies(@RequestBody Movies movies) throws IOException{
	
	 try {
         Movies savedMovies = moviesServices.createMovies(movies);
         return ResponseEntity.status(HttpStatus.CREATED).body(savedMovies);
     } catch (BadRequestException e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
     }
 }
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateMovie(@PathVariable Long id, @RequestBody Movies movieDeatiles){
		Movies movies = moviesRespository.findById(id)
		         .orElseThrow(() -> new ResourceNotFoundException("Movies not found by id..." + id));
		movies.setTitle(movieDeatiles.getTitle());
		movies.setGenre(movieDeatiles.getGenre());
		movies.setDuractionMinutes(movieDeatiles.getDuractionMinutes());
		
		 moviesRespository.save(movies);
		return ResponseEntity.ok("Movie with id " + id + " successfully updated");
				      
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteMovies(@PathVariable Long id){
		Movies movies = moviesRespository.findById(id)
		         .orElseThrow(() -> new ResourceNotFoundException("Already deleted or Movies not found by id..." + id));
   		moviesRespository.delete(movies);
   	 return ResponseEntity.ok("Movie with id " + id + " successfully deleted");

		
	}
	@GetMapping("/search/{title}")
	public ResponseEntity<List<Movies>> searchMovies(@PathVariable String title) {
	    return ResponseEntity.ok(moviesService.searchMoviesByTitle(title));
	}

}
