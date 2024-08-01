package de.cinema_booking.booking_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A Movie.
 */
@Entity
@Table(name = "movie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "movie_id")
    private UUID movieId;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "release_date")
    private ZonedDateTime releaseDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    @JsonIgnoreProperties(value = { "movie" }, allowSetters = true)
    private Set<Screen> screens = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    @JsonIgnoreProperties(value = { "movie", "customer" }, allowSetters = true)
    private Set<Booking> bookings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Movie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getMovieId() {
        return this.movieId;
    }

    public Movie movieId(UUID movieId) {
        this.setMovieId(movieId);
        return this;
    }

    public void setMovieId(UUID movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return this.title;
    }

    public Movie title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return this.genre;
    }

    public Movie genre(String genre) {
        this.setGenre(genre);
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Movie duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ZonedDateTime getReleaseDate() {
        return this.releaseDate;
    }

    public Movie releaseDate(ZonedDateTime releaseDate) {
        this.setReleaseDate(releaseDate);
        return this;
    }

    public void setReleaseDate(ZonedDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<Screen> getScreens() {
        return this.screens;
    }

    public void setScreens(Set<Screen> screens) {
        if (this.screens != null) {
            this.screens.forEach(i -> i.setMovie(null));
        }
        if (screens != null) {
            screens.forEach(i -> i.setMovie(this));
        }
        this.screens = screens;
    }

    public Movie screens(Set<Screen> screens) {
        this.setScreens(screens);
        return this;
    }

    public Movie addScreen(Screen screen) {
        this.screens.add(screen);
        screen.setMovie(this);
        return this;
    }

    public Movie removeScreen(Screen screen) {
        this.screens.remove(screen);
        screen.setMovie(null);
        return this;
    }

    public Set<Booking> getBookings() {
        return this.bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        if (this.bookings != null) {
            this.bookings.forEach(i -> i.setMovie(null));
        }
        if (bookings != null) {
            bookings.forEach(i -> i.setMovie(this));
        }
        this.bookings = bookings;
    }

    public Movie bookings(Set<Booking> bookings) {
        this.setBookings(bookings);
        return this;
    }

    public Movie addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setMovie(this);
        return this;
    }

    public Movie removeBooking(Booking booking) {
        this.bookings.remove(booking);
        booking.setMovie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        return getId() != null && getId().equals(((Movie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Movie{" +
            "id=" + getId() +
            ", movieId='" + getMovieId() + "'" +
            ", title='" + getTitle() + "'" +
            ", genre='" + getGenre() + "'" +
            ", duration=" + getDuration() +
            ", releaseDate='" + getReleaseDate() + "'" +
            "}";
    }
}
