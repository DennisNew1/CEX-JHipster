package de.cinema_booking.booking_system.domain;

import static de.cinema_booking.booking_system.domain.AssertUtils.zonedDataTimeSameInstant;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMovieAllPropertiesEquals(Movie expected, Movie actual) {
        assertMovieAutoGeneratedPropertiesEquals(expected, actual);
        assertMovieAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMovieAllUpdatablePropertiesEquals(Movie expected, Movie actual) {
        assertMovieUpdatableFieldsEquals(expected, actual);
        assertMovieUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMovieAutoGeneratedPropertiesEquals(Movie expected, Movie actual) {
        assertThat(expected)
            .as("Verify Movie auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMovieUpdatableFieldsEquals(Movie expected, Movie actual) {
        assertThat(expected)
            .as("Verify Movie relevant properties")
            .satisfies(e -> assertThat(e.getMovieId()).as("check movieId").isEqualTo(actual.getMovieId()))
            .satisfies(e -> assertThat(e.getTitle()).as("check title").isEqualTo(actual.getTitle()))
            .satisfies(e -> assertThat(e.getGenre()).as("check genre").isEqualTo(actual.getGenre()))
            .satisfies(e -> assertThat(e.getDuration()).as("check duration").isEqualTo(actual.getDuration()))
            .satisfies(
                e ->
                    assertThat(e.getReleaseDate())
                        .as("check releaseDate")
                        .usingComparator(zonedDataTimeSameInstant)
                        .isEqualTo(actual.getReleaseDate())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMovieUpdatableRelationshipsEquals(Movie expected, Movie actual) {}
}
