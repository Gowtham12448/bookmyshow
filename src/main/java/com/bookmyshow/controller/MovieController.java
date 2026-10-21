package com.bookmyshow.controller;

import com.bookmyshow.entity.Movie;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {

    private final MovieRepository movieRepo;
    private final ShowRepository showRepo;

    public MovieController(MovieRepository movieRepo, ShowRepository showRepo) {
        this.movieRepo = movieRepo;
        this.showRepo = showRepo;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("movies", movieRepo.findAll());
        return "home";
    }

    @GetMapping("/locations/{movieId}")
    public String locations(@PathVariable Long movieId, Model model) {
        Movie movie = movieRepo.findById(movieId).orElseThrow();
        List<String> locations = showRepo.findDistinctLocations();
        model.addAttribute("movie", movie);
        model.addAttribute("locations", locations);
        return "locations";
    }

    @GetMapping("/theaters/{movieId}")
    public String theaters(@PathVariable Long movieId,
                           @RequestParam String location, Model model) {
        Movie movie = movieRepo.findById(movieId).orElseThrow();
        var shows = showRepo.findByMovieAndLocation(movieId, location);
        // Group distinct theaters
        var theaters = shows.stream().map(s -> s.getTheater()).distinct().toList();
        model.addAttribute("movie", movie);
        model.addAttribute("location", location);
        model.addAttribute("theaters", theaters);
        return "theaters";
    }

    @GetMapping("/shows/{movieId}")
    public String shows(@PathVariable Long movieId,
                        @RequestParam Long theaterId, Model model) {
        Movie movie = movieRepo.findById(movieId).orElseThrow();
        var shows = showRepo.findByTheaterIdAndMovieId(theaterId, movieId);
        model.addAttribute("movie", movie);
        model.addAttribute("shows", shows);
        return "shows";
    }
}