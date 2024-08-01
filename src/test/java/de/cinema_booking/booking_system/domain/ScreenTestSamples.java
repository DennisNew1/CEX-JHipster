package de.cinema_booking.booking_system.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ScreenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Screen getScreenSample1() {
        return new Screen().id(1L).screenID(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).name("name1").totalSeats(1);
    }

    public static Screen getScreenSample2() {
        return new Screen().id(2L).screenID(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).name("name2").totalSeats(2);
    }

    public static Screen getScreenRandomSampleGenerator() {
        return new Screen()
            .id(longCount.incrementAndGet())
            .screenID(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .totalSeats(intCount.incrementAndGet());
    }
}
