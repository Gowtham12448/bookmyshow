package com.bookmyshow.repository;

import com.bookmyshow.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE " +
           "(:q IS NULL OR :q = '' OR LOWER(m.title) LIKE LOWER(CONCAT('%', :q, '%')) " +
           " OR LOWER(m.genre) LIKE LOWER(CONCAT('%', :q, '%'))) " +
           "AND (:lang IS NULL OR :lang = '' OR m.language = :lang)")
    List<Movie> search(@Param("q") String q, @Param("lang") String lang);

    List<Movie> findByFeaturedTrue();

    @Query("SELECT DISTINCT m.language FROM Movie m ORDER BY m.language")
    List<String> findDistinctLanguages();
}
