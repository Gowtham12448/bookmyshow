package com.bookmyshow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Theater {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;         // PVR, INOX, Cinepolis, AMB, AAA, SVC, etc.
    private String location;      // city
    private String area;          // neighbourhood
    private String address;
    private int totalSeats;
    private double rating;        // e.g. 4.5
    private String amenities;     // comma separated

    @Column(length = 500)
    private String imageUrl;
}
