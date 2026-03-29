package com.yupathbuilder.backend.integration.store;

import com.yupathbuilder.backend.course_catalog.store.CourseDetailsStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("sql-it")
class SqlCourseDetailsStoreIT {

    @Autowired
    private CourseDetailsStore courseDetailsStore;

    @Test
    void getDetailsReturnsSeededCourseAndSections() {
        var dto = courseDetailsStore.getDetails("EECS 1011", "FALL", 2026);

        assertNotNull(dto);
        assertEquals("EECS 1011", dto.courseCode());
        assertFalse(dto.sections().isEmpty());
        assertFalse(dto.sections().get(0).meetings().isEmpty());
    }

    @Test
    void getDetailsReturnsNullWhenCourseMissing() {
        assertNull(courseDetailsStore.getDetails("NOPE 9999", "FALL", 2026));
    }
}

