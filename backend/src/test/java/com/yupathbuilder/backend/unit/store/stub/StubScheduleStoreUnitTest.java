package com.yupathbuilder.backend.unit.store.stub;

import com.yupathbuilder.backend.store.stub.StubScheduleStore;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StubScheduleStoreUnitTest {

    private final StubScheduleStore store = new StubScheduleStore();

    @Test
    void buildCreatesChosenSectionsForProvidedCourseCodes() {
        var response = store.build("FALL 2026", List.of("EECS 1011", "MATH 1013"));

        assertEquals("FALL 2026", response.term());
        assertEquals(2, response.chosenSections().size());
        assertEquals("EECS 1011", response.chosenSections().get(0).courseCode());
    }

    @Test
    void buildIgnoresNullAndBlankCodes() {
        var response = store.build("FALL 2026", Arrays.asList("EECS 1011", "  ", null));

        assertEquals(1, response.chosenSections().size());
    }
}