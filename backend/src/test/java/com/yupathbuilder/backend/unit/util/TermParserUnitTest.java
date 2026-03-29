package com.yupathbuilder.backend.unit.util;

import com.yupathbuilder.backend.scheduler_system.model.Season;
import com.yupathbuilder.backend.util.TermParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TermParserUnitTest {

    @Test
    void parseAcceptsValidTerm() {
        var key = TermParser.parse("FALL 2026");
        assertEquals(Season.FALL, key.season());
        assertEquals(2026, key.year());
    }

    @Test
    void parseRejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> TermParser.parse(null));
    }

    @Test
    void parseRejectsWrongShape() {
        assertThrows(IllegalArgumentException.class, () -> TermParser.parse("FALL2026"));
    }

    @Test
    void parseRejectsUnknownSeason() {
        assertThrows(IllegalArgumentException.class, () -> TermParser.parse("SPRING 2026"));
    }
}

