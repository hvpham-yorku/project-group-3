package com.yupathbuilder.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    @Test
    void constructorRejectsInvalidRanges() {
        assertThrows(IllegalArgumentException.class, () ->
                new TimeSlot(null, 60, 120));

        assertThrows(IllegalArgumentException.class, () ->
                new TimeSlot(TimeSlot.Day.MON, -1, 120));

        assertThrows(IllegalArgumentException.class, () ->
                new TimeSlot(TimeSlot.Day.MON, 60, 60)); // end must be > start

        assertThrows(IllegalArgumentException.class, () ->
                new TimeSlot(TimeSlot.Day.MON, 60, 2000)); // end out of range
    }

    @Test
    void overlapsSameDayOnly() {
        TimeSlot a = new TimeSlot(TimeSlot.Day.MON, 600, 660); // 10:00-11:00
        TimeSlot b = new TimeSlot(TimeSlot.Day.MON, 650, 720); // 10:50-12:00
        TimeSlot c = new TimeSlot(TimeSlot.Day.TUE, 650, 720);

        assertTrue(a.overlaps(b));
        assertFalse(a.overlaps(c));
    }

    @Test
    void touchingEdgesIsNotOverlap() {
        TimeSlot a = new TimeSlot(TimeSlot.Day.WED, 600, 660); // 10:00-11:00
        TimeSlot b = new TimeSlot(TimeSlot.Day.WED, 660, 720); // 11:00-12:00

        assertFalse(a.overlaps(b));
        assertFalse(b.overlaps(a));
    }
}
