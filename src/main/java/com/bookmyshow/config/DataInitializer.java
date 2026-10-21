package com.bookmyshow.config;

import com.bookmyshow.entity.*;
import com.bookmyshow.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MovieRepository movieRepo;
    private final TheaterRepository theaterRepo;
    private final ShowRepository showRepo;
    private final SeatRepository seatRepo;
    private final Random rng = new Random(42);

    public DataInitializer(MovieRepository m, TheaterRepository t,
                           ShowRepository s, SeatRepository st) {
        this.movieRepo = m; this.theaterRepo = t; this.showRepo = s; this.seatRepo = st;
    }

    @Override
    public void run(String... args) {
        if (movieRepo.count() > 0) return;

        // ---------- MOVIES ----------
        List<Movie> movies = List.of(
            Movie.builder().title("Pushpa 2: The Rule").language("Telugu").genre("Action/Drama").duration("3h 20m")
                .rating(8.9).certificate("UA").featured(true)
                .description("Pushpa Raj returns to defend his red sandalwood empire against new rivals.")
                .posterUrl("https://image.tmdb.org/t/p/w500/kNv1i3wKGYKBjB9pQf5OTBAyWtS.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/b2Y2b3uEBKbFGQ7oAsZrVNvZjq1.jpg")
                .build(),
            Movie.builder().title("RRR").language("Telugu").genre("Action/Drama").duration("3h 7m")
                .rating(9.0).certificate("UA").featured(true)
                .description("A fictionalized retelling of two Indian revolutionaries — Alluri Sitarama Raju and Komaram Bheem.")
                .posterUrl("https://image.tmdb.org/t/p/w500/nEufeZlyAOLqO2brrs0yeF1lgXO.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/5Lgz8ZDHX5d2iUqEjOWuBjwmHk4.jpg")
                .build(),
            Movie.builder().title("Kalki 2898 AD").language("Telugu").genre("Sci-Fi/Action").duration("3h 1m")
                .rating(8.3).certificate("UA").featured(true)
                .description("A futuristic epic inspired by Hindu mythology — the last hope of humanity.")
                .posterUrl("https://image.tmdb.org/t/p/w500/4P4Q7kKGXKFhKZQOj2LrXf8b7bY.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/4P4Q7kKGXKFhKZQOj2LrXf8b7bY.jpg")
                .build(),
            Movie.builder().title("Salaar: Part 1").language("Telugu").genre("Action/Crime").duration("2h 55m")
                .rating(8.1).certificate("A").featured(true)
                .description("Two friends — one destined to rule, the other to obey.")
                .posterUrl("https://image.tmdb.org/t/p/w500/7ZtNSLRWCZUsFd6R7uIp7H6fM7d.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/7ZtNSLRWCZUsFd6R7uIp7H6fM7d.jpg")
                .build(),
            Movie.builder().title("Hanu-Man").language("Telugu").genre("Superhero/Fantasy").duration("2h 30m")
                .rating(8.4).certificate("UA").featured(true)
                .description("A young man gains the powers of Lord Hanuman.")
                .posterUrl("https://image.tmdb.org/t/p/w500/9g7b8Xj9Vz2g6Xb7yQWbVXcxJGi.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/9g7b8Xj9Vz2g6Xb7yQWbVXcxJGi.jpg")
                .build(),
            Movie.builder().title("Baahubali 2").language("Telugu").genre("Epic/Action").duration("2h 47m")
                .rating(9.2).certificate("UA")
                .description("Why did Kattappa kill Baahubali?")
                .posterUrl("https://image.tmdb.org/t/p/w500/u2e8X3F1zxOvTgQp0s1DmpCiQqQ.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/u2e8X3F1zxOvTgQp0s1DmpCiQqQ.jpg")
                .build(),
            Movie.builder().title("Jawan").language("Hindi").genre("Action/Thriller").duration("2h 49m")
                .rating(8.0).certificate("UA").featured(true)
                .description("A man is driven by a personal vendetta to rectify the wrongs in society.")
                .posterUrl("https://image.tmdb.org/t/p/w500/jFt1gS4BGHlK8xt76Y81Alp4dbt.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/jFt1gS4BGHlK8xt76Y81Alp4dbt.jpg")
                .build(),
            Movie.builder().title("Pathaan").language("Hindi").genre("Action/Thriller").duration("2h 26m")
                .rating(7.8).certificate("UA")
                .description("An exiled RAW agent returns to stop a rogue threat.")
                .posterUrl("https://image.tmdb.org/t/p/w500/mw0gzfmq4bYVpGvFvZGXa5WDpQn.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/mw0gzfmq4bYVpGvFvZGXa5WDpQn.jpg")
                .build(),
            Movie.builder().title("Vikram").language("Tamil").genre("Action/Thriller").duration("2h 55m")
                .rating(8.4).certificate("UA")
                .description("A special agent investigates a series of murders.")
                .posterUrl("https://image.tmdb.org/t/p/w500/k9bFWmGYbT4ozL02zPr0vGDwvG7.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/k9bFWmGYbT4ozL02zPr0vGDwvG7.jpg")
                .build(),
            Movie.builder().title("Ponniyin Selvan 2").language("Tamil").genre("Historical/Drama").duration("2h 50m")
                .rating(8.2).certificate("UA")
                .description("The Chola empire's most iconic moments brought to life.")
                .posterUrl("https://image.tmdb.org/t/p/w500/uKvBfRPbKXwUQRC5fjkZWiSJAPj.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/uKvBfRPbKXwUQRC5fjkZWiSJAPj.jpg")
                .build(),
            Movie.builder().title("Kantara").language("Kannada").genre("Action/Drama").duration("2h 28m")
                .rating(8.7).certificate("UA")
                .description("A tribal folklore from coastal Karnataka.")
                .posterUrl("https://image.tmdb.org/t/p/w500/AtB6Lgd4mEwQ7lTsMFwlbG3VFxS.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/AtB6Lgd4mEwQ7lTsMFwlbG3VFxS.jpg")
                .build(),
            Movie.builder().title("Animal").language("Hindi").genre("Action/Crime").duration("3h 21m")
                .rating(7.9).certificate("A")
                .description("A son's obsessive bond with his father.")
                .posterUrl("https://image.tmdb.org/t/p/w500/hr9rjR3J0xBBKmlJ4zE20BtKc1C.jpg")
                .bannerUrl("https://image.tmdb.org/t/p/original/hr9rjR3J0xBBKmlJ4zE20BtKc1C.jpg")
                .build()
        );
        movieRepo.saveAll(movies);

        // ---------- CITIES ----------
        List<String> cities = List.of(
            // Andhra Pradesh
            "Srikakulam", "Vizianagaram", "Parvathipuram", "Bobbili", "Visakhapatnam",
            "Anakapalli", "Rajahmundry", "Kakinada", "Eluru", "Vijayawada", "Guntur",
            "Tenali", "Ongole", "Nellore", "Tirupati", "Kadapa", "Kurnool", "Anantapur",
            "Chittoor", "Machilipatnam", "Bhimavaram", "Tadepalligudem", "Amalapuram",
            "Proddatur", "Hindupur", "Nandyal", "Adoni", "Madanapalle",
            // Telangana
            "Hyderabad", "Secunderabad", "Warangal", "Karimnagar", "Nizamabad",
            "Khammam", "Ramagundam", "Mahbubnagar", "Nalgonda", "Adilabad",
            "Suryapet", "Siddipet", "Miryalaguda", "Jagtial", "Mancherial", "Nirmal",
            // Metro / other states
            "Chennai", "Bengaluru", "Mumbai", "Delhi", "Pune", "Kolkata", "Ahmedabad",
            "Kochi", "Coimbatore", "Mysuru"
        );

        // ---------- THEATER BRANDS with AREAS ----------
        String[] brands = {
            "PVR Cinemas", "INOX", "Cinepolis", "AMB Cinemas", "AAA Cinemas",
            "SVC Cinemas", "Asian Cinemas", "Sri Venkateswara", "Miraj Cinemas",
            "MovieMax", "PVP Cinema", "Sangam Cinemas", "BIG Cinemas", "Sri Sakthi"
        };

        String[] areas = {
            "MG Road", "Main Road", "Mall Road", "Station Road", "Ring Road",
            "Beach Road", "Cinema Hall Street", "Market Road", "Bazaar Street", "Temple Street"
        };

        String[] amenitiesPool = {
            "Dolby Atmos", "4K Laser", "Recliner Seats", "Parking", "Food Court",
            "Wheelchair Access", "Online Snacks", "AC", "IMAX"
        };

        for (String city : cities) {
            int theaterCount = 3 + rng.nextInt(3);   // 3–5 theaters per city
            for (int i = 0; i < theaterCount; i++) {
                String brand = brands[rng.nextInt(brands.length)];
                String area = areas[rng.nextInt(areas.length)];
                String amenities = String.join(", ",
                        amenitiesPool[rng.nextInt(amenitiesPool.length)],
                        amenitiesPool[rng.nextInt(amenitiesPool.length)],
                        amenitiesPool[rng.nextInt(amenitiesPool.length)]);
                Theater theater = Theater.builder()
                    .brand(brand)
                    .name(brand + " " + area)
                    .location(city)
                    .area(area)
                    .address(area + ", " + city + ", " + (isTelangana(city) ? "Telangana" : isAP(city) ? "Andhra Pradesh" : "India"))
                    .totalSeats(60)
                    .rating(3.5 + rng.nextDouble() * 1.5)
                    .amenities(amenities)
                    .imageUrl("https://picsum.photos/seed/" + city.replaceAll("\\s","") + i + "/400/250")
                    .build();
                theaterRepo.save(theater);
            }
        }

        // ---------- SHOWS + SEATS ----------
        LocalDate today = LocalDate.now();
        LocalTime[] slots = {
            LocalTime.of(9, 0), LocalTime.of(12, 30), LocalTime.of(15, 45),
            LocalTime.of(18, 30), LocalTime.of(21, 45)
        };
        double[] priceTiers = {120, 150, 180, 220, 260, 300};

        List<Theater> theaters = theaterRepo.findAll();
        for (Movie movie : movies) {
            // Each movie plays in ~30% of theaters (random, seeded)
            for (Theater theater : theaters) {
                if (rng.nextDouble() > 0.35) continue;

                int showsPerDay = 1 + rng.nextInt(3); // 1–3 shows per theater per day
                int days = 2; // 2 days of shows
                for (int d = 0; d < days; d++) {
                    for (int s = 0; s < showsPerDay; s++) {
                        Show show = Show.builder()
                            .movie(movie)
                            .theater(theater)
                            .showDate(today.plusDays(d))
                            .showTime(slots[rng.nextInt(slots.length)])
                            .price(round2(priceTiers[rng.nextInt(priceTiers.length)] + rng.nextDouble() * 40))
                            .build();
                        showRepo.save(show);

                        List<Seat> seats = new ArrayList<>();
                        char[] rows = {'A','B','C','D','E','F'};
                        for (char row : rows) {
                            for (int n = 1; n <= 10; n++) {
                                seats.add(Seat.builder()
                                    .show(show).seatNumber(row + String.valueOf(n))
                                    .booked(false).build());
                            }
                        }
                        seatRepo.saveAll(seats);
                    }
                }
            }
        }
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    private static boolean isTelangana(String c) {
        return Arrays.asList("Hyderabad","Secunderabad","Warangal","Karimnagar","Nizamabad",
            "Khammam","Ramagundam","Mahbubnagar","Nalgonda","Adilabad","Suryapet",
            "Siddipet","Miryalaguda","Jagtial","Mancherial","Nirmal").contains(c);
    }

    private static boolean isAP(String c) {
        return Arrays.asList("Srikakulam","Vizianagaram","Parvathipuram","Bobbili",
            "Visakhapatnam","Anakapalli","Rajahmundry","Kakinada","Eluru","Vijayawada",
            "Guntur","Tenali","Ongole","Nellore","Tirupati","Kadapa","Kurnool",
            "Anantapur","Chittoor","Machilipatnam","Bhimavaram","Tadepalligudem",
            "Amalapuram","Proddatur","Hindupur","Nandyal","Adoni","Madanapalle").contains(c);
    }
}
