package com.yupathbuilder.backend.model;

import java.util.Objects;


/**
 * Domain model: Term.
 *
 * <p>Simple immutable data structures used throughout the backend.
 */

public class Term implements Comparable<Term> {

    public enum Season { WINTER, SUMMER, FALL }

    private Season season;
    private int year;

    public Term() {}

    public Term(Season season, int year) {
        if (season == null) throw new IllegalArgumentException("season cannot be null");
        if (year < 1900 || year > 3000) throw new IllegalArgumentException("year out of range");
        this.season = season;
        this.year = year;
    }

    public Season getSeason() { return season; }
    public void setSeason(Season season) { this.season = season; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public int compareTo(Term other) {
        if (other == null) return 1;

        // Compare by year first
        int byYear = Integer.compare(this.year, other.year);
        if (byYear != 0) return byYear;

        // Same year: compare by season order
        return Integer.compare(seasonOrder(this.season), seasonOrder(other.season));
    }

    private int seasonOrder(Season s) {
        // Chronological within a year (typical): WINTER -> SUMMER -> FALL
        return switch (s) {
            case WINTER -> 1;
            case SUMMER -> 2;
            case FALL -> 3;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Term term)) return false;
        return year == term.year && season == term.season;
    }

    @Override
    public int hashCode() {
        return Objects.hash(season, year);
    }
}
