package com.bookmyshow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String language;
    private String genre;
    private String duration;
    private double rating;

    @Column(length = 1000)
    private String description;

    @Column(length = 500)
    private String posterUrl;

    @Column(length = 500)
    private String bannerUrl;

    @Column(length = 500)
    private String trailerUrl;

    private String certificate; // U, UA, A
    private boolean featured;   // for hero carousel
}
