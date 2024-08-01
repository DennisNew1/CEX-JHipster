package de.cinema_booking.booking_system.domain;

import static de.cinema_booking.booking_system.domain.BookingTestSamples.*;
import static de.cinema_booking.booking_system.domain.MovieTestSamples.*;
import static de.cinema_booking.booking_system.domain.ScreenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import de.cinema_booking.booking_system.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MovieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Movie.class);
        Movie movie1 = getMovieSample1();
        Movie movie2 = new Movie();
        assertThat(movie1).isNotEqualTo(movie2);

        movie2.setId(movie1.getId());
        assertThat(movie1).isEqualTo(movie2);

        movie2 = getMovieSample2();
        assertThat(movie1).isNotEqualTo(movie2);
    }

    @Test
    void screenTest() {
        Movie movie = getMovieRandomSampleGenerator();
        Screen screenBack = getScreenRandomSampleGenerator();

        movie.addScreen(screenBack);
        assertThat(movie.getScreens()).containsOnly(screenBack);
        assertThat(screenBack.getMovie()).isEqualTo(movie);

        movie.removeScreen(screenBack);
        assertThat(movie.getScreens()).doesNotContain(screenBack);
        assertThat(screenBack.getMovie()).isNull();

        movie.screens(new HashSet<>(Set.of(screenBack)));
        assertThat(movie.getScreens()).containsOnly(screenBack);
        assertThat(screenBack.getMovie()).isEqualTo(movie);

        movie.setScreens(new HashSet<>());
        assertThat(movie.getScreens()).doesNotContain(screenBack);
        assertThat(screenBack.getMovie()).isNull();
    }

    @Test
    void bookingTest() {
        Movie movie = getMovieRandomSampleGenerator();
        Booking bookingBack = getBookingRandomSampleGenerator();

        movie.addBooking(bookingBack);
        assertThat(movie.getBookings()).containsOnly(bookingBack);
        assertThat(bookingBack.getMovie()).isEqualTo(movie);

        movie.removeBooking(bookingBack);
        assertThat(movie.getBookings()).doesNotContain(bookingBack);
        assertThat(bookingBack.getMovie()).isNull();

        movie.bookings(new HashSet<>(Set.of(bookingBack)));
        assertThat(movie.getBookings()).containsOnly(bookingBack);
        assertThat(bookingBack.getMovie()).isEqualTo(movie);

        movie.setBookings(new HashSet<>());
        assertThat(movie.getBookings()).doesNotContain(bookingBack);
        assertThat(bookingBack.getMovie()).isNull();
    }
}
