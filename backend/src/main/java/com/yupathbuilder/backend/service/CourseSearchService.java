package com.yupathbuilder.backend.service;

import com.yupathbuilder.backend.model.Course;
import com.yupathbuilder.backend.repo.CourseRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


/**
 * Service layer component: CourseSearchService.
 *
 * <p>Contains business logic (e.g., searching courses, building schedules, detecting conflicts).
 */

public class CourseSearchService {

    private final CourseRepository repo;

    public CourseSearchService(CourseRepository repo) {
        this.repo = repo;
    }

    public List<Course> search(String q) {
        if (q == null || q.trim().isEmpty()) return repo.findAll();
        String query = q.trim().toLowerCase(Locale.ROOT);

        return repo.findAll().stream()
                .filter(c -> c != null && (
                        safe(c.courseCode()).toLowerCase(Locale.ROOT).contains(query) ||
                        safe(c.title()).toLowerCase(Locale.ROOT).contains(query)
                ))
                .collect(Collectors.toList());
    }

    private String safe(String s) { return s == null ? "" : s; }
}
