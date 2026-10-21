package com.bookmyshow.repository;

import com.bookmyshow.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
    List<Theater> findByLocation(String location);
}