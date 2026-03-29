package com.yupathbuilder.backend.integration.store;

import com.yupathbuilder.backend.course_catalog.store.CourseStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("sql-it")
class SqlCourseStoreIT {

    @Autowired
    private CourseStore courseStore;

    @Test
    void listCoursesReadsSeededRowsFromRealDatabase() {
        var courses = courseStore.listCourses();

        assertFalse(courses.isEmpty());
        assertTrue(courses.stream().anyMatch(c -> "EECS 1011".equals(c.getCourseCode())));
        assertTrue(courses.stream().anyMatch(c -> "KINE 1000".equals(c.getCourseCode())));
    }

    @Test
    void searchCoursesFiltersSeededRowsFromRealDatabase() {
        var results = courseStore.searchCourses("security");

        assertFalse(results.isEmpty());
        assertTrue(results.stream().anyMatch(c -> "EECS 3482".equals(c.getCourseCode()) || "EECS 4481".equals(c.getCourseCode()) || "EECS 4482".equals(c.getCourseCode())));
    }
}

