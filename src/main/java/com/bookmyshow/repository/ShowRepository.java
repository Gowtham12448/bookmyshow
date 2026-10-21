package com.bookmyshow.repository;

import com.bookmyshow.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("SELECT s FROM Show s WHERE s.movie.id = :movieId AND s.theater.location = :location")
    List<Show> findByMovieAndLocation(@Param("movieId") Long movieId, @Param("location") String location);

    List<Show> findByTheaterIdAndMovieId(Long theaterId, Long movieId);

    @Query("SELECT DISTINCT s.theater.location FROM Show s")
    List<String> findDistinctLocations();
}