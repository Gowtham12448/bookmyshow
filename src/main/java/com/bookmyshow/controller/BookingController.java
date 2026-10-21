package com.bookmyshow.controller;

import com.bookmyshow.entity.Booking;
import com.bookmyshow.entity.Seat;
import com.bookmyshow.entity.Show;
import com.bookmyshow.entity.User;
import com.bookmyshow.repository.SeatRepository;
import com.bookmyshow.repository.ShowRepository;
import com.bookmyshow.service.BookingService;
import com.bookmyshow.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookingController {

    private final ShowRepository showRepo;
    private final SeatRepository seatRepo;
    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(ShowRepository showRepo,
                             SeatRepository seatRepo,
                             BookingService bookingService,
                             UserService userService) {
        this.showRepo = showRepo;
        this.seatRepo = seatRepo;
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping("/seats/{showId}")
    public String seats(@PathVariable Long showId, Model model) {
        Show show = showRepo.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found: " + showId));
        List<Seat> seats = seatRepo.findByShowId(showId);
        model.addAttribute("show", show);
        model.addAttribute("seats", seats);
        return "seats";
    }

    @PostMapping("/payment")
    public String payment(@RequestParam Long showId,
                          @RequestParam(required = false) List<String> selectedSeats,
                          Model model) {
        if (selectedSeats == null || selectedSeats.isEmpty()) {
            return "redirect:/seats/" + showId + "?error=noSeatsSelected";
        }
        Show show = showRepo.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found: " + showId));
        model.addAttribute("show", show);
        model.addAttribute("selectedSeats", selectedSeats);
        model.addAttribute("totalAmount", show.getPrice() * selectedSeats.size());
        return "payment";
    }

    @PostMapping("/confirm-booking")
    public String confirm(@RequestParam Long showId,
                          @RequestParam List<String> selectedSeats,
                          @RequestParam String paymentMethod,
                          Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        String txnId = "TXN" + System.currentTimeMillis();
        Booking booking = bookingService.createBooking(user, showId, selectedSeats, paymentMethod, txnId);
        return "redirect:/ticket/" + booking.getId();
    }

    /** ✅ FIXED — now correctly loads booking and verifies ownership */
    @GetMapping("/ticket/{bookingId}")
    public String ticket(@PathVariable Long bookingId,
                         Authentication auth,
                         Model model) {
        Booking booking = bookingService.getBooking(bookingId);
        User user = userService.findByUsername(auth.getName());

        // Only allow the owner to view their own ticket
        if (!booking.getUser().getId().equals(user.getId())) {
            return "redirect:/history";
        }

        model.addAttribute("booking", booking);
        return "ticket";
    }

    @GetMapping("/history")
    public String history(Authentication auth, Model model) {
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("bookings", bookingService.getUserBookings(user.getId()));
        model.addAttribute("user", user);
        return "history";
    }

    /** ✅ FIXED — verify ownership before cancelling */
    @PostMapping("/cancel/{bookingId}")
    public String cancel(@PathVariable Long bookingId, Authentication auth) {
        Booking booking = bookingService.getBooking(bookingId);
        User user = userService.findByUsername(auth.getName());

        if (!booking.getUser().getId().equals(user.getId())) {
            return "redirect:/history";
        }

        bookingService.cancelBooking(bookingId);
        return "redirect:/history";
    }
}