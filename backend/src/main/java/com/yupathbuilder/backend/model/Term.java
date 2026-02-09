package com.yupathbuilder.backend.model;

import java.util.Objects;

public final class Term implements Comparable<Term> {

    public enum Season { WINTER, SUMMER, FALL }

    private final Season season;
    private final int year;

    public Term(Season season, int year) {
        if (season == null) throw new IllegalArgumentException("season cannot be null");
        if (year < 1900 || year > 3000) throw new IllegalArgumentException("year out of range");
        this.season = season;
        this.year = year;
    }

    public Season getSeason() {
        return season;
    }

    public int getYear() {
        return year;
    }

    @Override
    public int compareTo(Term other) {
        if (other == null) throw new NullPointerException("other term is null");
        int yearCmp = Integer.compare(this.year, other.year);
        if (yearCmp != 0) return yearCmp;
        return Integer.compare(seasonOrder(this.season), seasonOrder(other.season));
    }

    private static int seasonOrder(Season s) {
        // York usually goes Winter -> Summer -> Fall
        return switch (s) {
            case WINTER -> 0;
            case SUMMER -> 1;
            case FALL -> 2;
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

    @Override
    public String toString() {
        return season + " " + year;
    }
}
