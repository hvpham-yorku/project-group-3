package com.yupathbuilder.backend.model;

import org.junit.jupiter.api.Test;

import com.yupathbuilder.backend.model.Term;
import com.yupathbuilder.backend.model.Term.Season;

import static org.junit.jupiter.api.Assertions.*;

class TermTest {

    @Test
    void termsShouldSortByYearThenSeason() {
        Term a = new Term(Term.Season.WINTER, 2026);
        Term b = new Term(Term.Season.FALL, 2026);
        Term c = new Term(Term.Season.WINTER, 2027);

        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(c) < 0);
    }
}
