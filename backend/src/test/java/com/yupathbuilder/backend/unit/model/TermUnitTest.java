package com.yupathbuilder.backend.unit.model;

import com.yupathbuilder.backend.model.Term;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TermUnitTest {

    @Test
    void compareToOrdersByYearThenSeason() {
        Term winter2026 = new Term(Term.Season.WINTER, 2026);
        Term fall2026 = new Term(Term.Season.FALL, 2026);
        Term winter2027 = new Term(Term.Season.WINTER, 2027);

        assertTrue(winter2026.compareTo(fall2026) < 0);
        assertTrue(fall2026.compareTo(winter2027) < 0);
    }

    @Test
    void equalsAndHashCodeUseSeasonAndYear() {
        Term a = new Term(Term.Season.WINTER, 2027);
        Term b = new Term(Term.Season.WINTER, 2027);
        Term c = new Term(Term.Season.FALL, 2026);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
