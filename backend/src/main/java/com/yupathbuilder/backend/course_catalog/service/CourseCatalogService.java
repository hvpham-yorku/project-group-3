package com.yupathbuilder.backend.course_catalog.service;

import com.yupathbuilder.backend.course_catalog.dto.CourseDto;
import com.yupathbuilder.backend.course_catalog.store.CourseStore;
import com.yupathbuilder.backend.scheduler_system.model.Season;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Coordinates catalog listing and search operations for course endpoints.
 *
 * <p>This service insulates controllers from the active storage implementation
 * and maps course entities into API-facing DTOs.</p>
 */
@Service
public class CourseCatalogService {

    private final CourseStore courseStore;

    public CourseCatalogService(CourseStore courseStore) {
        this.courseStore = courseStore;
    }

    /**
     * Returns the full catalog or a filtered subset when a query is supplied.
     */
    public List<CourseDto> listCourses(String q) {
        var courses = (q == null || q.isBlank())
            ? courseStore.listCourses()
            : courseStore.searchCourses(q);

        return courses.stream()
            .map(c -> new CourseDto(c.getCourseCode(), c.getTitle(), c.getDescription()))
            .toList();
    }

    /**
     * Performs an explicit search and returns an empty list for blank queries to
     * avoid unintentionally returning the full catalog.
     */
    public List<CourseDto> searchCourses(String q) {
        if (q == null || q.trim().isEmpty()) {
            return List.of();
        }

        return courseStore.searchCourses(q).stream()
            .map(c -> new CourseDto(c.getCourseCode(), c.getTitle(), c.getDescription()))
            .toList();
    }

    /**
     * Performs an explicit search scoped to a term when season and year are
     * supplied.
     */
    public List<CourseDto> searchCourses(String q, String season, Integer year) {
        if (q == null || q.trim().isEmpty()) {
            return List.of();
        }

        Season parsedSeason = season == null || season.isBlank() ? null : Season.parse(season);

        return courseStore.searchCourses(q, parsedSeason, year).stream()
            .map(c -> new CourseDto(c.getCourseCode(), c.getTitle(), c.getDescription()))
            .toList();
    }
}
