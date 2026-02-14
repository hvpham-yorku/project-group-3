package com.yupathbuilder.backend.service;

import com.yupathbuilder.backend.model.Conflict;
import com.yupathbuilder.backend.model.Section;
import com.yupathbuilder.backend.model.TimeSlot;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ConflictDetector (pure JUnit, no Spring).
 *
 * These tests validate the overlap rules:
 * - Different days => no conflict
 * - Partial overlap on same day => conflict with correct overlap window
 * - Touching edges (end == start) => not a conflict
 */
class ConflictDetectorTest {

    private final ConflictDetector detector = new ConflictDetector();

    @Test
    void noConflictsWhenDifferentDays() {
        // Arrange: same time but different days
        Section existing = new Section(
                "S1", "EECS2030", "W2026", "TBD",
                "Mon", LocalTime.of(10, 0), LocalTime.of(11, 0),
                "LAS-1000", 120
        );

        Section incoming = new Section(
                "S2", "EECS2021", "W2026", "TBD",
                "Tue", LocalTime.of(10, 0), LocalTime.of(11, 0),
                "LAS-1000", 120
        );

        // Act
        List<Conflict> conflicts = detector.detectConflicts(List.of(existing), incoming);

        // Assert
        assertTrue(conflicts.isEmpty(), "Expected no conflicts when days do not overlap");
    }

    @Test
    void detectsOverlapOnSameDay() {
        // Arrange: overlap on Monday from 11:00 to 11:15
        Section existing = new Section(
                "S1", "EECS2030", "W2026", "TBD",
                "Mon", LocalTime.of(10, 0), LocalTime.of(11, 15),
                "LAS-1000", 120
        );

        Section incoming = new Section(
                "S2", "EECS2021", "W2026", "TBD",
                "Mon", LocalTime.of(11, 0), LocalTime.of(12, 0),
                "LAS-1000", 120
        );

        // Act
        List<Conflict> conflicts = detector.detectConflicts(List.of(existing), incoming);

        // Assert: exactly one conflict
        assertEquals(1, conflicts.size(), "Expected one overlap conflict on the same day");

        TimeSlot clash = conflicts.get(0).getClashOn();
        assertNotNull(clash, "Conflict should include a clash TimeSlot");

        assertEquals(TimeSlot.Day.MON, clash.getDay(), "Clash day should be Monday");

        // Overlap window expected: 11:00 -> 11:15
        assertEquals(11 * 60, clash.getStartMin(), "Clash start minute should be 11:00");
        assertEquals(11 * 60 + 15, clash.getEndMin(), "Clash end minute should be 11:15");
    }

    @Test
    void touchingEdgesIsNotAConflict() {
        // Arrange: existing ends exactly when incoming starts
        Section existing = new Section(
                "S1", "EECS2030", "W2026", "TBD",
                "Wed", LocalTime.of(10, 0), LocalTime.of(11, 0),
                "LAS-1000", 120
        );

        Section incoming = new Section(
                "S2", "EECS2021", "W2026", "TBD",
                "Wed", LocalTime.of(11, 0), LocalTime.of(12, 0),
                "LAS-1000", 120
        );

        // Act
        List<Conflict> conflicts = detector.detectConflicts(List.of(existing), incoming);

        // Assert
        assertTrue(conflicts.isEmpty(), "Expected no conflict when intervals only touch at the boundary");
    }
}
