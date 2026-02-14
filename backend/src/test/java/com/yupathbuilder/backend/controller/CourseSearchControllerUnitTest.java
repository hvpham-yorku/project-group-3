package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.model.Course;
import com.yupathbuilder.backend.service.CourseSearchService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pure JUnit test for CourseSearchController (no Spring context, no MockMvc).
 *
 * Notes:
 * - Mockito is used to fake the service.
 * - Course is a Java record, so accessor is courseCode() (not getCourseCode()).
 */
class CourseSearchControllerUnitTest {

    @Test
    void search_callsService_andReturnsResults() {
        // Arrange
        CourseSearchService service = mock(CourseSearchService.class);
        CourseSearchController controller = new CourseSearchController(service);

        when(service.search("EECS")).thenReturn(List.of(
                new Course("EECS2030", "Advanced OOP", 3.0, "EECS", "")
        ));

        // Act
        // IMPORTANT: your controller method name might be different.
        // If "search" doesn't compile, open CourseSearchController.java and use its real method name.
        List<Course> result = controller.search("EECS");

        // Assert
        assertEquals(1, result.size());
        assertEquals("EECS2030", result.get(0).courseCode());
        verify(service).search("EECS");
    }

    @Test
    void search_nullQuery_passesNullToService() {
        // Arrange
        CourseSearchService service = mock(CourseSearchService.class);
        CourseSearchController controller = new CourseSearchController(service);

        when(service.search(null)).thenReturn(List.of());

        // Act
        List<Course> result = controller.search(null);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(service).search(null);
    }
}
