package com.bookmyshow.config;

import com.bookmyshow.entity.*;
import com.bookmyshow.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MovieRepository movieRepo;
    private final TheaterRepository theaterRepo;
    private final ShowRepository showRepo;
    private final SeatRepository seatRepo;

    public DataInitializer(MovieRepository m, TheaterRepository t,
                           ShowRepository s, SeatRepository st) {
        this.movieRepo = m; this.theaterRepo = t; this.showRepo = s; this.seatRepo = st;
    }

    @Override
    public void run(String... args) {
        if (movieRepo.count() > 0) return;

        // Movies (Indian - Telugu/Hindi/Tamil)
        List<Movie> movies = List.of(
            Movie.builder().title("Pushpa 2: The Rule").language("Telugu").genre("Action").duration("3h 20m").rating(8.5)
                .description("Pushpa Raj returns to rule the red sandalwood smuggling empire.")
                .posterUrl("https://image.tmdb.org/t/p/w500/1p5DBJQYQm5rWMuOnYKr7vSGZ7R.jpg").build(),
            Movie.builder().title("RRR").language("Telugu").genre("Action/Drama").duration("3h 7m").rating(8.9)
                .description("A tale of two Indian revolutionaries.")
                .posterUrl("https://image.tmdb.org/t/p/w500/nEufeZlyAOLqO2brrs0yeF1lgXO.jpg").build(),
            Movie.builder().title("Kalki 2898 AD").language("Telugu").genre("Sci-Fi").duration("3h").rating(8.2)
                .description("A futuristic epic inspired by Indian mythology.")
                .posterUrl("https://image.tmdb.org/t/p/w500/4P4Q7kKGXKFhKZQOj2LrXf8b7bY.jpg").build(),
            Movie.builder().title("Salaar").language("Telugu").genre("Action").duration("2h 55m").rating(7.8)
                .description("Two friends, one clash of empires.")
                .posterUrl("https://image.tmdb.org/t/p/w500/7ZtNSLRWCZUsFd6R7uIp7H6fM7d.jpg").build(),
            Movie.builder().title("Hanu-Man").language("Telugu").genre("Superhero").duration("2h 30m").rating(8.4)
                .description("A young man gains superpowers from Lord Hanuman.")
                .posterUrl("https://image.tmdb.org/t/p/w500/9g7b8Xj9Vz2g6Xb7yQWbVXcxJGi.jpg").build(),
            Movie.builder().title("Baahubali 2").language("Telugu").genre("Epic").duration("2h 47m").rating(9.1)
                .description("Why did Kattappa kill Baahubali?")
                .posterUrl("https://image.tmdb.org/t/p/w500/u2e8X3F1zxOvTgQp0s1DmpCiQqQ.jpg").build()
        );
        movieRepo.saveAll(movies);

        // Andhra + Telangana locations
        String[] locations = {
            "Srikakulam", "Vizianagaram", "Parvathipuram", "Bobbili",
            "Visakhapatnam", "Vijayawada", "Guntur", "Nellore",
            "Tirupathi", "Ongole", "Eluru", "Rajahmundry",
            "Kakinada", "Hyderabad", "Warangal"
        };

        int theaterIndex = 0;
        for (String loc : locations) {
            // 2 theaters per location
            for (int t = 1; t <= 2; t++) {
                Theater theater = Theater.builder()
                        .name("PVR " + loc + " Mall " + t)
                        .location(loc)
                        .address("Main Road, " + loc)
                        .totalSeats(60)
                        .build();
                theaterRepo.save(theater);
                theaterIndex++;
            }
        }

        // Create shows for each movie in each theater
        LocalDate today = LocalDate.now();
        LocalTime[] times = { LocalTime.of(10, 0), LocalTime.of(14, 0), LocalTime.of(18, 0), LocalTime.of(21, 30) };

        for (Theater theater : theaterRepo.findAll()) {
            for (Movie movie : movies) {
                // pick 2 random showtimes per movie per theater
                for (int i = 0; i < 2; i++) {
                    Show show = Show.builder()
                            .movie(movie)
                            .theater(theater)
                            .showDate(today.plusDays(i))
                            .showTime(times[(int)(Math.random() * times.length)])
                            .price(150 + Math.random() * 150)
                            .build();
                    showRepo.save(show);

                    // create seats
                    List<Seat> seats = new ArrayList<>();
                    char[] rows = {'A','B','C','D','E','F'};
                    for (char row : rows) {
                        for (int n = 1; n <= 10; n++) {
                            seats.add(Seat.builder()
                                    .show(show)
                                    .seatNumber(row + String.valueOf(n))
                                    .booked(false).build());
                        }
                    }
                    seatRepo.saveAll(seats);
                }
            }
        }
    }
}