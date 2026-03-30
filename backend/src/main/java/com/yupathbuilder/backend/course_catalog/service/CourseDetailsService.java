package com.yupathbuilder.backend.course_catalog.service;

import com.yupathbuilder.backend.course_catalog.dto.CourseDetailsDto;
import com.yupathbuilder.backend.course_catalog.store.CourseDetailsStore;
import org.springframework.stereotype.Service;

/**
 * Provides course detail lookups for a specific course and academic term.
 */
@Service
public class CourseDetailsService {

    private final CourseDetailsStore courseDetailsStore;

    public CourseDetailsService(CourseDetailsStore courseDetailsStore) {
        this.courseDetailsStore = courseDetailsStore;
    }

    /**
     * Retrieves the detailed course representation for the requested term.
     */
    public CourseDetailsDto getDetails(String courseCode, String season, int year) {
        return courseDetailsStore.getDetails(courseCode, season, year);
    }
}
