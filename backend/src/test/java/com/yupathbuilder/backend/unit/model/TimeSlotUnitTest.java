package com.yupathbuilder.backend.unit.model;

import com.yupathbuilder.backend.model.TimeSlot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotUnitTest {

    @Test
    void constructorRejectsEndBeforeStart() {
        assertThrows(IllegalArgumentException.class,
                () -> new TimeSlot(TimeSlot.Day.MON, 660, 600));
    }

    @Test
    void overlapsReturnsTrueForSameDayOverlappingTimes() {
        TimeSlot a = new TimeSlot(TimeSlot.Day.MON, 570, 630);
        TimeSlot b = new TimeSlot(TimeSlot.Day.MON, 600, 660);

        assertTrue(a.overlaps(b));
    }

    @Test
    void overlapsReturnsFalseForDifferentDays() {
        TimeSlot a = new TimeSlot(TimeSlot.Day.MON, 570, 630);
        TimeSlot b = new TimeSlot(TimeSlot.Day.TUE, 600, 660);

        assertFalse(a.overlaps(b));
    }

    @Test
    void overlapsReturnsFalseWhenOneEndsExactlyWhenTheOtherStarts() {
        TimeSlot a = new TimeSlot(TimeSlot.Day.MON, 570, 630);
        TimeSlot b = new TimeSlot(TimeSlot.Day.MON, 630, 690);

        assertFalse(a.overlaps(b));
    }
}
