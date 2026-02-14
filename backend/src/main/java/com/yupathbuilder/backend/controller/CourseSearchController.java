package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.model.Course;
import com.yupathbuilder.backend.service.CourseSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for the CourseSearchController endpoints.
 *
 * <p>This class maps HTTP requests to service/repository calls and returns DTOs.
 * Keep the controller thin: validation + orchestration only.
 */

@RestController
@RequestMapping("/api/search")
public class CourseSearchController {

    private final CourseSearchService service;

    public CourseSearchController(CourseSearchService service) {
        this.service = service;
    }

    @GetMapping("/courses")
    public List<Course> search(@RequestParam(name = "q", required = false) String q) {
        return service.search(q);
    }
}
