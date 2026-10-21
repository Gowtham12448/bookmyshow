package com.bookmyshow.repository;

import com.bookmyshow.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

    List<Theater> findByLocation(String location);

    @Query("SELECT t FROM Theater t WHERE " +
           "(:q IS NULL OR :q = '' OR LOWER(t.name) LIKE LOWER(CONCAT('%', :q, '%')) " +
           " OR LOWER(t.brand) LIKE LOWER(CONCAT('%', :q, '%'))) " +
           "AND (:loc IS NULL OR :loc = '' OR t.location = :loc)")
    List<Theater> search(@Param("q") String q, @Param("loc") String loc);
}
