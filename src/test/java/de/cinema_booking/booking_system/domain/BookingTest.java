package de.cinema_booking.booking_system.domain;

import static de.cinema_booking.booking_system.domain.BookingTestSamples.*;
import static de.cinema_booking.booking_system.domain.CustomerTestSamples.*;
import static de.cinema_booking.booking_system.domain.MovieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import de.cinema_booking.booking_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Booking.class);
        Booking booking1 = getBookingSample1();
        Booking booking2 = new Booking();
        assertThat(booking1).isNotEqualTo(booking2);

        booking2.setId(booking1.getId());
        assertThat(booking1).isEqualTo(booking2);

        booking2 = getBookingSample2();
        assertThat(booking1).isNotEqualTo(booking2);
    }

    @Test
    void movieTest() {
        Booking booking = getBookingRandomSampleGenerator();
        Movie movieBack = getMovieRandomSampleGenerator();

        booking.setMovie(movieBack);
        assertThat(booking.getMovie()).isEqualTo(movieBack);

        booking.movie(null);
        assertThat(booking.getMovie()).isNull();
    }

    @Test
    void customerTest() {
        Booking booking = getBookingRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        booking.setCustomer(customerBack);
        assertThat(booking.getCustomer()).isEqualTo(customerBack);

        booking.customer(null);
        assertThat(booking.getCustomer()).isNull();
    }
}
