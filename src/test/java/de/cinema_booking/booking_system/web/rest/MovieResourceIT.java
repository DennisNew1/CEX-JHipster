package de.cinema_booking.booking_system.web.rest;

import static de.cinema_booking.booking_system.domain.MovieAsserts.*;
import static de.cinema_booking.booking_system.web.rest.TestUtil.createUpdateProxyForBean;
import static de.cinema_booking.booking_system.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cinema_booking.booking_system.IntegrationTest;
import de.cinema_booking.booking_system.domain.Movie;
import de.cinema_booking.booking_system.repository.MovieRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MovieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MovieResourceIT {

    private static final UUID DEFAULT_MOVIE_ID = UUID.randomUUID();
    private static final UUID UPDATED_MOVIE_ID = UUID.randomUUID();

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_GENRE = "AAAAAAAAAA";
    private static final String UPDATED_GENRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final ZonedDateTime DEFAULT_RELEASE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RELEASE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/movies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovieMockMvc;

    private Movie movie;

    private Movie insertedMovie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createEntity(EntityManager em) {
        Movie movie = new Movie()
            .movieId(DEFAULT_MOVIE_ID)
            .title(DEFAULT_TITLE)
            .genre(DEFAULT_GENRE)
            .duration(DEFAULT_DURATION)
            .releaseDate(DEFAULT_RELEASE_DATE);
        return movie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movie createUpdatedEntity(EntityManager em) {
        Movie movie = new Movie()
            .movieId(UPDATED_MOVIE_ID)
            .title(UPDATED_TITLE)
            .genre(UPDATED_GENRE)
            .duration(UPDATED_DURATION)
            .releaseDate(UPDATED_RELEASE_DATE);
        return movie;
    }

    @BeforeEach
    public void initTest() {
        movie = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedMovie != null) {
            movieRepository.delete(insertedMovie);
            insertedMovie = null;
        }
    }

    @Test
    @Transactional
    void createMovie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Movie
        var returnedMovie = om.readValue(
            restMovieMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movie)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Movie.class
        );

        // Validate the Movie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMovieUpdatableFieldsEquals(returnedMovie, getPersistedMovie(returnedMovie));

        insertedMovie = returnedMovie;
    }

    @Test
    @Transactional
    void createMovieWithExistingId() throws Exception {
        // Create the Movie with an existing ID
        movie.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movie)))
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMovies() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.saveAndFlush(movie);

        // Get all the movieList
        restMovieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movie.getId().intValue())))
            .andExpect(jsonPath("$.[*].movieId").value(hasItem(DEFAULT_MOVIE_ID.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(sameInstant(DEFAULT_RELEASE_DATE))));
    }

    @Test
    @Transactional
    void getMovie() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.saveAndFlush(movie);

        // Get the movie
        restMovieMockMvc
            .perform(get(ENTITY_API_URL_ID, movie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movie.getId().intValue()))
            .andExpect(jsonPath("$.movieId").value(DEFAULT_MOVIE_ID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.releaseDate").value(sameInstant(DEFAULT_RELEASE_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingMovie() throws Exception {
        // Get the movie
        restMovieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMovie() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.saveAndFlush(movie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movie
        Movie updatedMovie = movieRepository.findById(movie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMovie are not directly saved in db
        em.detach(updatedMovie);
        updatedMovie
            .movieId(UPDATED_MOVIE_ID)
            .title(UPDATED_TITLE)
            .genre(UPDATED_GENRE)
            .duration(UPDATED_DURATION)
            .releaseDate(UPDATED_RELEASE_DATE);

        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMovie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMovieToMatchAllProperties(updatedMovie);
    }

    @Test
    @Transactional
    void putNonExistingMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(put(ENTITY_API_URL_ID, movie.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movie)))
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(movie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(movie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMovieWithPatch() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.saveAndFlush(movie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movie using partial update
        Movie partialUpdatedMovie = new Movie();
        partialUpdatedMovie.setId(movie.getId());

        partialUpdatedMovie.title(UPDATED_TITLE).genre(UPDATED_GENRE).releaseDate(UPDATED_RELEASE_DATE);

        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovieUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMovie, movie), getPersistedMovie(movie));
    }

    @Test
    @Transactional
    void fullUpdateMovieWithPatch() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.saveAndFlush(movie);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the movie using partial update
        Movie partialUpdatedMovie = new Movie();
        partialUpdatedMovie.setId(movie.getId());

        partialUpdatedMovie
            .movieId(UPDATED_MOVIE_ID)
            .title(UPDATED_TITLE)
            .genre(UPDATED_GENRE)
            .duration(UPDATED_DURATION)
            .releaseDate(UPDATED_RELEASE_DATE);

        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMovie.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMovie))
            )
            .andExpect(status().isOk());

        // Validate the Movie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMovieUpdatableFieldsEquals(partialUpdatedMovie, getPersistedMovie(partialUpdatedMovie));
    }

    @Test
    @Transactional
    void patchNonExistingMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, movie.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(movie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(movie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMovie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        movie.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMovieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(movie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Movie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMovie() throws Exception {
        // Initialize the database
        insertedMovie = movieRepository.saveAndFlush(movie);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the movie
        restMovieMockMvc
            .perform(delete(ENTITY_API_URL_ID, movie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return movieRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Movie getPersistedMovie(Movie movie) {
        return movieRepository.findById(movie.getId()).orElseThrow();
    }

    protected void assertPersistedMovieToMatchAllProperties(Movie expectedMovie) {
        assertMovieAllPropertiesEquals(expectedMovie, getPersistedMovie(expectedMovie));
    }

    protected void assertPersistedMovieToMatchUpdatableProperties(Movie expectedMovie) {
        assertMovieAllUpdatablePropertiesEquals(expectedMovie, getPersistedMovie(expectedMovie));
    }
}
