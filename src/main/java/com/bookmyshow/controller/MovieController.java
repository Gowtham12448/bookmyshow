package com.bookmyshow.controller;

import com.bookmyshow.entity.Movie;
import com.bookmyshow.entity.Show;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.repository.ShowRepository;
import com.bookmyshow.repository.TheaterRepository;
import com.bookmyshow.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {

    private final MovieRepository movieRepo;
    private final TheaterRepository theaterRepo;
    private final ShowRepository showRepo;
    private final MovieService movieService;

    public MovieController(MovieRepository movieRepo, TheaterRepository theaterRepo,
                           ShowRepository showRepo, MovieService movieService) {
        this.movieRepo = movieRepo;
        this.theaterRepo = theaterRepo;
        this.showRepo = showRepo;
        this.movieService = movieService;
    }

    @GetMapping("/home")
    public String home(@RequestParam(required = false) String q,
                       @RequestParam(required = false) String language,
                       Model model) {
        List<Movie> movies = movieService.search(q, language);
        model.addAttribute("movies", movies);
        model.addAttribute("featured", movieService.getFeatured());
        model.addAttribute("languages", movieService.getLanguages());
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("selectedLanguage", language == null ? "" : language);
        return "home";
    }

    @GetMapping("/locations/{movieId}")
    public String locations(@PathVariable Long movieId,
                            @RequestParam(required = false) String q,
                            Model model) {
        Movie movie = movieRepo.findById(movieId).orElseThrow();
        List<String> locations = showRepo.findDistinctLocations();
        if (q != null && !q.isBlank()) {
            String query = q.trim().toLowerCase();
            locations = locations.stream()
                    .filter(l -> l.toLowerCase().contains(query)).toList();
        }
        model.addAttribute("movie", movie);
        model.addAttribute("locations", locations);
        model.addAttribute("q", q == null ? "" : q);
        return "locations";
    }

    @GetMapping("/theaters/{movieId}")
    public String theaters(@PathVariable Long movieId,
                           @RequestParam String location,
                           @RequestParam(required = false) String q,
                           Model model) {
        Movie movie = movieRepo.findById(movieId).orElseThrow();
        List<Show> shows = showRepo.findByMovieAndLocation(movieId, location);

        var theaterIds = shows.stream().map(s -> s.getTheater().getId()).distinct().toList();
        var theaters = theaterRepo.findAllById(theaterIds);

        if (q != null && !q.isBlank()) {
            String query = q.trim().toLowerCase();
            theaters = theaters.stream()
                    .filter(t -> t.getName().toLowerCase().contains(query)
                              || t.getBrand().toLowerCase().contains(query))
                    .toList();
        }

        model.addAttribute("movie", movie);
        model.addAttribute("location", location);
        model.addAttribute("theaters", theaters);
        model.addAttribute("q", q == null ? "" : q);
        return "theaters";
    }

    @GetMapping("/shows/{movieId}")
    public String shows(@PathVariable Long movieId,
                        @RequestParam Long theaterId, Model model) {
        Movie movie = movieRepo.findById(movieId).orElseThrow();
        var shows = showRepo.findByTheaterIdAndMovieId(theaterId, movieId);
        var theater = theaterRepo.findById(theaterId).orElseThrow();
        model.addAttribute("movie", movie);
        model.addAttribute("theater", theater);
        model.addAttribute("shows", shows);
        return "shows";
    }
}
