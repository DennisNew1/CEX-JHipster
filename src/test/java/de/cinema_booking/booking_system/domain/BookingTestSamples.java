package de.cinema_booking.booking_system.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BookingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Booking getBookingSample1() {
        return new Booking()
            .id(1L)
            .bookingID(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .seatID(1)
            .paymentStatus("paymentStatus1");
    }

    public static Booking getBookingSample2() {
        return new Booking()
            .id(2L)
            .bookingID(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .seatID(2)
            .paymentStatus("paymentStatus2");
    }

    public static Booking getBookingRandomSampleGenerator() {
        return new Booking()
            .id(longCount.incrementAndGet())
            .bookingID(UUID.randomUUID())
            .seatID(intCount.incrementAndGet())
            .paymentStatus(UUID.randomUUID().toString());
    }
}
