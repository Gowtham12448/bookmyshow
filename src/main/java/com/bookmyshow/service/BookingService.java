package com.bookmyshow.service;

import com.bookmyshow.entity.Booking;
import com.bookmyshow.entity.Payment;
import com.bookmyshow.entity.Seat;
import com.bookmyshow.entity.Show;
import com.bookmyshow.entity.User;
import com.bookmyshow.repository.BookingRepository;
import com.bookmyshow.repository.PaymentRepository;
import com.bookmyshow.repository.SeatRepository;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final SeatRepository seatRepo;
    private final ShowRepository showRepo;
    private final PaymentRepository paymentRepo;

    public BookingService(BookingRepository bookingRepo,
                          SeatRepository seatRepo,
                          ShowRepository showRepo,
                          PaymentRepository paymentRepo) {
        this.bookingRepo = bookingRepo;
        this.seatRepo = seatRepo;
        this.showRepo = showRepo;
        this.paymentRepo = paymentRepo;
    }

    @Transactional
    public Booking createBooking(User user, Long showId, List<String> seats,
                                 String paymentMethod, String transactionId) {
        Show show = showRepo.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found: " + showId));

        if (seats == null || seats.isEmpty()) {
            throw new RuntimeException("No seats selected");
        }

        // Validate no seat is already booked
        List<Seat> showSeats = seatRepo.findByShowId(showId);
        for (Seat seat : showSeats) {
            if (seats.contains(seat.getSeatNumber()) && seat.isBooked()) {
                throw new RuntimeException("Seat already booked: " + seat.getSeatNumber());
            }
        }

        double amount = show.getPrice() * seats.size();

        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .seats(seats)
                .totalAmount(amount)
                .bookingTime(LocalDateTime.now())
                .status("BOOKED")
                .ticketNumber("BMS" + System.currentTimeMillis())
                .build();
        bookingRepo.save(booking);

        // Mark selected seats as booked
        for (Seat seat : showSeats) {
            if (seats.contains(seat.getSeatNumber())) {
                seat.setBooked(true);
                seatRepo.save(seat);
            }
        }

        // Save payment record
        Payment p = Payment.builder()
                .booking(booking)
                .paymentMethod(paymentMethod)
                .transactionId(transactionId)
                .amount(amount)
                .status("SUCCESS")
                .paymentTime(LocalDateTime.now())
                .build();
        paymentRepo.save(p);

        return booking;
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        if ("CANCELLED".equals(booking.getStatus())) {
            return;
        }

        booking.setStatus("CANCELLED");
        bookingRepo.save(booking);

        // Free up seats
        List<Seat> seats = seatRepo.findByShowId(booking.getShow().getId());
        for (Seat s : seats) {
            if (booking.getSeats().contains(s.getSeatNumber())) {
                s.setBooked(false);
                seatRepo.save(s);
            }
        }
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepo.findByUserIdOrderByBookingTimeDesc(userId);
    }

    /** ✅ NEW — used by BookingController to render the ticket page */
    public Booking getBooking(Long id) {
        return bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));
    }
}