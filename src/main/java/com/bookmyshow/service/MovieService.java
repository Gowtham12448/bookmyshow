package com.bookmyshow.service;

import com.bookmyshow.entity.Movie;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepo;

    public MovieService(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    public Movie getMovie(Long id) {
        return movieRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found: " + id));
    }

    public Movie save(Movie movie) {
        return movieRepo.save(movie);
    }
}