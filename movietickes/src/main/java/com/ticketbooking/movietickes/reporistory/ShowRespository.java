package com.ticketbooking.movietickes.reporistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticketbooking.movietickes.entity.Show;

public interface ShowRespository extends JpaRepository<Show, Long> {

}
