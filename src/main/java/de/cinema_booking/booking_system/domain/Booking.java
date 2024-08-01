package de.cinema_booking.booking_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "booking_id")
    private UUID bookingID;

    @Column(name = "movie_time")
    private ZonedDateTime movieTime;

    @Column(name = "seat_id")
    private Integer seatID;

    @Column(name = "payment_status")
    private String paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "screens", "bookings" }, allowSetters = true)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bookings" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Booking id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getBookingID() {
        return this.bookingID;
    }

    public Booking bookingID(UUID bookingID) {
        this.setBookingID(bookingID);
        return this;
    }

    public void setBookingID(UUID bookingID) {
        this.bookingID = bookingID;
    }

    public ZonedDateTime getMovieTime() {
        return this.movieTime;
    }

    public Booking movieTime(ZonedDateTime movieTime) {
        this.setMovieTime(movieTime);
        return this;
    }

    public void setMovieTime(ZonedDateTime movieTime) {
        this.movieTime = movieTime;
    }

    public Integer getSeatID() {
        return this.seatID;
    }

    public Booking seatID(Integer seatID) {
        this.setSeatID(seatID);
        return this;
    }

    public void setSeatID(Integer seatID) {
        this.seatID = seatID;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public Booking paymentStatus(String paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Booking movie(Movie movie) {
        this.setMovie(movie);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Booking customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return getId() != null && getId().equals(((Booking) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", bookingID='" + getBookingID() + "'" +
            ", movieTime='" + getMovieTime() + "'" +
            ", seatID=" + getSeatID() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            "}";
    }
}
