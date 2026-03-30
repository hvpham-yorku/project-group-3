package com.yupathbuilder.backend.course_catalog.controller;

import com.yupathbuilder.backend.course_catalog.dto.CourseDetailsDto;
import com.yupathbuilder.backend.course_catalog.service.CourseDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Exposes detailed course information, including section and meeting data for a
 * requested term.
 */
@RestController
@RequestMapping("/api/courses")
public class CourseDetailsController {

  private final CourseDetailsService courseDetailsService;

  public CourseDetailsController(CourseDetailsService courseDetailsService) {
    this.courseDetailsService = courseDetailsService;
  }

  /**
   * Returns detailed course data for the requested term or a 404-style response
   * when the course cannot be resolved.
   */
  @GetMapping("/{courseCode}/details")
  public ResponseEntity<?> details(
      @PathVariable String courseCode,
      @RequestParam String season,
      @RequestParam int year
  ) {
    CourseDetailsDto dto = courseDetailsService.getDetails(courseCode, season, year);
    if (dto == null) return ResponseEntity.status(404).body("Course not found: " + courseCode);
    return ResponseEntity.ok(dto);
  }
}

