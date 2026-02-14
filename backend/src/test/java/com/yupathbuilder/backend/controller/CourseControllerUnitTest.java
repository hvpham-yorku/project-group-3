package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.model.Course;
import com.yupathbuilder.backend.repo.CourseRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pure JUnit test for CourseController (no Spring context, no MockMvc).
 *
 * Course is a Java record, so accessors are courseCode(), title(), etc.
 */
class CourseControllerUnitTest {

    @Test
    void list_returnsRepoData() {
        // Arrange
        CourseRepository repo = mock(CourseRepository.class);
        CourseController controller = new CourseController(repo);

        // Course is a record: constructor requires all components
        Course c = new Course(
                "EECS2001",
                "Computer Organization",
                3.0,
                "EECS",
                ""
        );

        when(repo.findAll()).thenReturn(List.of(c));

        // Act
        List<Course> result = controller.list();

        // Assert
        assertEquals(1, result.size(), "Should return the same number of courses as the repository");
        assertEquals("EECS2001", result.get(0).courseCode(), "First course code should match expected value");
        verify(repo).findAll();
    }
}
