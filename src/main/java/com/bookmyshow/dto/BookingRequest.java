package com.bookmyshow.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
    private Long showId;
    private List<String> selectedSeats;
    private String paymentMethod;
}