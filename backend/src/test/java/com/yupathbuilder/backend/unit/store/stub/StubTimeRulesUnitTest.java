package com.yupathbuilder.backend.unit.store.stub;

import com.yupathbuilder.backend.store.stub.StubTimeRules;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StubTimeRulesUnitTest {

    @Test
    void slotForReturnsKnownFallSlot() {
        var slot = StubTimeRules.slotFor("EECS 1011", "FALL");

        assertEquals("A", slot.section());
        assertFalse(slot.meetings().isEmpty());
        assertEquals("MON", slot.meetings().get(0).day());
    }

    @Test
    void slotForFallsBackToGeneratedSlotForUnknownCourse() {
        var slot = StubTimeRules.slotFor("ZZZZ 9999", "FALL");

        assertNotNull(slot.section());
        assertFalse(slot.meetings().isEmpty());
    }
}
