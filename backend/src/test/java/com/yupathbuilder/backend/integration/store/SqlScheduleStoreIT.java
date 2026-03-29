package com.yupathbuilder.backend.integration.store;

import com.yupathbuilder.backend.store.ScheduleStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("sql-it")
class SqlScheduleStoreIT {

    @Autowired
    private ScheduleStore scheduleStore;

    @Test
    void buildCreatesScheduleFromRealDatabase() {
        var response = scheduleStore.build("FALL 2026", List.of("EECS 1011", "MATH 1013"));

        assertEquals("FALL 2026", response.term());
        assertEquals(2, response.chosenSections().size());
        assertEquals("EECS 1011", response.chosenSections().get(0).courseCode());
    }

    @Test
    void buildCreatesScheduleForSummerTerm() {
        var response = scheduleStore.build("SUMMER 2027", List.of("EECS 1011", "MATH 1013"));

        assertEquals("SUMMER 2027", response.term());
        assertEquals(2, response.chosenSections().size());
        assertEquals("EECS 1011", response.chosenSections().get(0).courseCode());
    }
}
