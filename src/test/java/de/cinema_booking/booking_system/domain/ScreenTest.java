package de.cinema_booking.booking_system.domain;

import static de.cinema_booking.booking_system.domain.MovieTestSamples.*;
import static de.cinema_booking.booking_system.domain.ScreenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import de.cinema_booking.booking_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScreenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Screen.class);
        Screen screen1 = getScreenSample1();
        Screen screen2 = new Screen();
        assertThat(screen1).isNotEqualTo(screen2);

        screen2.setId(screen1.getId());
        assertThat(screen1).isEqualTo(screen2);

        screen2 = getScreenSample2();
        assertThat(screen1).isNotEqualTo(screen2);
    }

    @Test
    void movieTest() {
        Screen screen = getScreenRandomSampleGenerator();
        Movie movieBack = getMovieRandomSampleGenerator();

        screen.setMovie(movieBack);
        assertThat(screen.getMovie()).isEqualTo(movieBack);

        screen.movie(null);
        assertThat(screen.getMovie()).isNull();
    }
}
