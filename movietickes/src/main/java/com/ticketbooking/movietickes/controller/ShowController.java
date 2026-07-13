package com.ticketbooking.movietickes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ticketbooking.movietickes.entity.Show;
import com.ticketbooking.movietickes.service.ShowService;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(showService.getAllShow());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getfindById(id));
    }
    
    @PostMapping("/add")
    public ResponseEntity<Show> createShow(@RequestBody Show show) {
        Show savedShow = showService.createShow(show);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedShow);
    }

}