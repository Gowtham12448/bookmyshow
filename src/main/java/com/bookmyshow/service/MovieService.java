package com.bookmyshow.service;

import com.bookmyshow.entity.Movie;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository repo;

    public MovieService(MovieRepository repo) {
        this.repo = repo;
    }

    public List<Movie> getAllMovies() { return repo.findAll(); }

    public List<Movie> search(String q, String language) {
        return repo.search(blankToNull(q), blankToNull(language));
    }

    public List<Movie> getFeatured() { return repo.findByFeaturedTrue(); }

    public List<String> getLanguages() { return repo.findDistinctLanguages(); }

    public Movie getMovie(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Movie not found: " + id));
    }

    private String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
