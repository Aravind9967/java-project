package com.ticketbooking.movietickes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ticketbooking.movietickes.entity.Theaters;
import com.ticketbooking.movietickes.exceptions.InvalidTheatersException;
import com.ticketbooking.movietickes.exceptions.ResourceNotFoundException;
import com.ticketbooking.movietickes.exceptions.TheatersAlreadyExistsException;
import com.ticketbooking.movietickes.reporistory.TheatersRespository;

@Service
public class TheatersService {

    private final TheatersRespository theatersRespository;

    public TheatersService(TheatersRespository theatersRespository) {
        this.theatersRespository = theatersRespository;
    }

    // Get all theaters
    public List<Theaters> getAllTheaters() {
        return theatersRespository.findAll();
    }

    // Get theater by ID
    public Theaters getTheatersById(Long id) {
        return theatersRespository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Theater not found with id: " + id));
    }

    // Create theater
    public Theaters createTheater(Theaters theaters) {

        // Validate theater name
        if (theaters.getName() == null || theaters.getName().trim().isEmpty()) {
            throw new InvalidTheatersException("Theater name cannot be empty");
        }

        // Validate city
        if (theaters.getCity() == null || theaters.getCity().trim().isEmpty()) {
            throw new InvalidTheatersException("City cannot be empty");
        }

        // Validate total seats
        if (theaters.getTotalSeats() <= 0) {
            throw new InvalidTheatersException("Total seats must be greater than zero");
        }

        // Trim values
        String theaterName = theaters.getName().trim();
        String city = theaters.getCity().trim();

        // Check duplicate theater
        boolean exists = theatersRespository.findAll().stream()
                .anyMatch(t ->
                        t.getName().equalsIgnoreCase(theaterName)
                                && t.getCity().equalsIgnoreCase(city));

        if (exists) {
            throw new TheatersAlreadyExistsException(
                    "Theater '" + theaterName + "' already exists in " + city);
        }

        theaters.setName(theaterName);
        theaters.setCity(city);

        return theatersRespository.save(theaters);
    }

    // Update theater
    public Theaters updateTheater(Long id, Theaters theaterDetails) {

        Theaters theater = getTheatersById(id);

        if (theaterDetails.getName() == null || theaterDetails.getName().trim().isEmpty()) {
            throw new InvalidTheatersException("Theater name cannot be empty");
        }

        if (theaterDetails.getCity() == null || theaterDetails.getCity().trim().isEmpty()) {
            throw new InvalidTheatersException("City cannot be empty");
        }

        if (theaterDetails.getTotalSeats() <= 0) {
            throw new InvalidTheatersException("Total seats must be greater than zero");
        }

        theater.setName(theaterDetails.getName().trim());
        theater.setCity(theaterDetails.getCity().trim());
        theater.setTotalSeats(theaterDetails.getTotalSeats());

        return theatersRespository.save(theater);
    }

    // Delete theater
    public void deleteTheater(Long id) {

        Theaters theater = getTheatersById(id);

        theatersRespository.delete(theater);
    }

}