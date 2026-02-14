package com.yupathbuilder.backend.model;

import java.util.Objects;


/**
 * Domain model: TimeSlot.
 *
 * <p>Simple immutable data structures used throughout the backend.
 */

public final class TimeSlot {
    public enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

    private final Day day;
    private final int startMin;
    private final int endMin;

    public TimeSlot(Day day, int startMin, int endMin) {
        if (day == null) throw new IllegalArgumentException("day cannot be null");
        if (startMin < 0 || startMin > 1439) throw new IllegalArgumentException("startMin out of range");
        if (endMin < 0 || endMin > 1440) throw new IllegalArgumentException("endMin out of range");
        if (endMin <= startMin) throw new IllegalArgumentException("endMin must be > startMin");
        this.day = day;
        this.startMin = startMin;
        this.endMin = endMin;
    }

    public Day getDay() { return day; }
    public int getStartMin() { return startMin; }
    public int getEndMin() { return endMin; }

    public boolean overlaps(TimeSlot other) {
        if (other == null) return false;
        if (this.day != other.day) return false;
        return this.startMin < other.endMin && other.startMin < this.endMin;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot ts)) return false;
        return startMin == ts.startMin && endMin == ts.endMin && day == ts.day;
    }
    @Override public int hashCode() { return Objects.hash(day, startMin, endMin); }
}
