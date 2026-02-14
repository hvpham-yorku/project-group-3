package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.model.Course;
import com.yupathbuilder.backend.repo.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for the CourseController endpoints.
 *
 * <p>This class maps HTTP requests to service/repository calls and returns DTOs.
 * Keep the controller thin: validation + orchestration only.
 */

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courses;

    public CourseController(CourseRepository courses) {
        this.courses = courses;
    }

    @GetMapping
    public List<Course> list() {
        return courses.findAll();
    }
}
