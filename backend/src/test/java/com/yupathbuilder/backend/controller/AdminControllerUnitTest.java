package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.repo.CourseRepository;
import com.yupathbuilder.backend.repo.SectionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pure JUnit test for AdminController (no Spring context).
 *
 * We call the controller method directly (reload()) and verify its response.
 * This avoids MockMvc/@WebMvcTest setup for Iteration 1.
 */
class AdminControllerUnitTest {

    @Test
    void reload_whenReposNotFileBased_returns400WithMessage() {
        // Arrange: Mockito mocks are NOT instances of FileCourseRepository/FileSectionRepository,
        // so the controller should go to the "not file-based" branch.
        CourseRepository courseRepo = mock(CourseRepository.class);
        SectionRepository sectionRepo = mock(SectionRepository.class);

        AdminController controller = new AdminController(courseRepo, sectionRepo);

        // Act: IMPORTANT -> your controller method is named reload(), not reloadData().
        ResponseEntity<?> response = controller.reload();

        // Assert
        assertNotNull(response, "Controller should return a ResponseEntity");
        assertTrue(response.getStatusCode().is4xxClientError(),
                "Expected a 4xx response when repositories are not file-based");
        assertNotNull(response.getBody(), "Response body should explain the error");
        assertTrue(response.getBody().toString().toLowerCase().contains("file"),
                "Body should mention that repositories are not file-based");
    }
}
