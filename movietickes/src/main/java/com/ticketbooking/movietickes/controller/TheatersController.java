package com.ticketbooking.movietickes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ticketbooking.movietickes.entity.Theaters;
import com.ticketbooking.movietickes.exceptions.ResourceNotFoundException;
import com.ticketbooking.movietickes.reporistory.TheatersRespository;

@RestController
@RequestMapping("/api/theaters")
public class TheatersController {
	
	private final TheatersRespository theatersRespository;
	
	public TheatersController(TheatersRespository theatersRespository) {
		this.theatersRespository = theatersRespository;
	}
	@GetMapping("/all")
	public List<Theaters> getAllTheaters(){
		return theatersRespository.findAll();
	}
	
	@PostMapping("/add")
	public ResponseEntity<Theaters> createTheaters(@RequestBody Theaters theaters){
		Theaters savedTheaters = theatersRespository.save(theaters);
		return ResponseEntity.status(201).body(savedTheaters);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Theaters> getTheatersById(@PathVariable Long id){
		Theaters theaters = theatersRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("not found the theaters in this id"));
		return ResponseEntity.ok(theaters);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTheaters(@PathVariable Long id){
		Theaters theaters = theatersRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("not found the theaters in this id"));
		theatersRespository.delete(theaters);
		return ResponseEntity.ok("this id is successfully deleted..." +id);
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateTheaters(@PathVariable Long id, @RequestBody Theaters theatersDetailes){
		Theaters theaters = theatersRespository.findById(id)
		         .orElseThrow(() -> new ResourceNotFoundException("Movies not found by id..." + id));
		theaters.setName(theatersDetailes.getName());
		theaters.setCity(theatersDetailes.getCity());
		theaters.setTotalSeats(theatersDetailes.getTotalSeats());
		
		theatersRespository.save(theatersDetailes);
		return ResponseEntity.ok("Movie with id " + id + " successfully updated");
				      
	}
	
	

}
