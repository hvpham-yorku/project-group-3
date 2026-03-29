package com.yupathbuilder.backend.unit.store.stub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupathbuilder.backend.course_catalog.store.stub.StubCourseDetailsStore;
import com.yupathbuilder.backend.course_catalog.store.stub.StubCourseStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StubCourseDetailsStoreUnitTest {

    private final StubCourseDetailsStore store = new StubCourseDetailsStore(new StubCourseStore(new ObjectMapper()));

    @Test
    void getDetailsReturnsCourseAndDeterministicSection() {
        var dto = store.getDetails("EECS 1011", "FALL", 2026);

        assertNotNull(dto);
        assertEquals("EECS 1011", dto.courseCode());
        assertEquals("FALL 2026", dto.term());
        assertFalse(dto.sections().isEmpty());
        assertFalse(dto.sections().get(0).meetings().isEmpty());
    }

    @Test
    void getDetailsReturnsNullForUnknownCourse() {
        assertNull(store.getDetails("NOPE 9999", "FALL", 2026));
    }
}

