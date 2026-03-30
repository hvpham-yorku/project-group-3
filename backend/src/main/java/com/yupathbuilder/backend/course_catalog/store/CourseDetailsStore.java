package com.yupathbuilder.backend.course_catalog.store;

import com.yupathbuilder.backend.course_catalog.dto.CourseDetailsDto;

/**
 * Abstraction for retrieving course details from the active catalog source.
 */
public interface CourseDetailsStore {
  /**
   * Returns detailed course data for the requested term, or {@code null} when
   * the course cannot be found.
   */
  CourseDetailsDto getDetails(String courseCode, String season, int year);
}

