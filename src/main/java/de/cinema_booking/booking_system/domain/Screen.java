package de.cinema_booking.booking_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * A Screen.
 */
@Entity
@Table(name = "screen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Screen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "screen_id")
    private UUID screenID;

    @Column(name = "name")
    private String name;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "screens", "bookings" }, allowSetters = true)
    private Movie movie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Screen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getScreenID() {
        return this.screenID;
    }

    public Screen screenID(UUID screenID) {
        this.setScreenID(screenID);
        return this;
    }

    public void setScreenID(UUID screenID) {
        this.screenID = screenID;
    }

    public String getName() {
        return this.name;
    }

    public Screen name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalSeats() {
        return this.totalSeats;
    }

    public Screen totalSeats(Integer totalSeats) {
        this.setTotalSeats(totalSeats);
        return this;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Screen movie(Movie movie) {
        this.setMovie(movie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Screen)) {
            return false;
        }
        return getId() != null && getId().equals(((Screen) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Screen{" +
            "id=" + getId() +
            ", screenID='" + getScreenID() + "'" +
            ", name='" + getName() + "'" +
            ", totalSeats=" + getTotalSeats() +
            "}";
    }
}
