package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.repo.CourseRepository;
import com.yupathbuilder.backend.repo.SectionRepository;
import com.yupathbuilder.backend.repo.file.FileCourseRepository;
import com.yupathbuilder.backend.repo.file.FileSectionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller for the AdminController endpoints.
 *
 * <p>This class maps HTTP requests to service/repository calls and returns DTOs.
 * Keep the controller thin: validation + orchestration only.
 */

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;

    public AdminController(CourseRepository courseRepository, SectionRepository sectionRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
    }

    @PostMapping("/reload-data")
    public ResponseEntity<?> reload() {
        if (courseRepository instanceof FileCourseRepository fileCourses && sectionRepository instanceof FileSectionRepository fileSections) {
            fileCourses.reload();
            fileSections.reload();
            return ResponseEntity.ok("Reloaded CSV data");
        }
        return ResponseEntity.status(400).body("Repositories are not file-based");
    }
}
