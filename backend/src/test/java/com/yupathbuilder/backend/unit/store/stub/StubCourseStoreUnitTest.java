package com.yupathbuilder.backend.unit.store.stub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupathbuilder.backend.store.stub.StubCourseStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StubCourseStoreUnitTest {

    private final StubCourseStore store = new StubCourseStore(new ObjectMapper());

    @Test
    void listCoursesLoadsDefaultStubContent() {
        var courses = store.listCourses();

        assertFalse(courses.isEmpty());
        assertTrue(courses.stream().anyMatch(c -> "EECS 1011".equals(c.getCourseCode())));
        assertTrue(courses.stream().anyMatch(c -> "KINE 1000".equals(c.getCourseCode())));
    }

    @Test
    void searchCoursesMatchesByCourseCodeTitleAndDescriptionCaseInsensitively() {
        assertTrue(store.searchCourses("eecs 1011").stream().anyMatch(c -> "EECS 1011".equals(c.getCourseCode())));
        assertTrue(store.searchCourses("software design").stream().anyMatch(c -> "EECS 3311".equals(c.getCourseCode())));
        assertTrue(store.searchCourses("forensics").stream().anyMatch(c -> "EECS 4482".equals(c.getCourseCode())));
    }

    @Test
    void searchCoursesReturnsAllForBlankQuery() {
        assertEquals(store.listCourses().size(), store.searchCourses("   ").size());
    }
}
