package com.yupathbuilder.backend.course_catalog.controller;

import com.yupathbuilder.backend.course_catalog.dto.CourseDto;
import com.yupathbuilder.backend.course_catalog.service.CourseCatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exposes the course catalog listing endpoint used by the frontend course
 * browser.
 *
 * <p>This controller delegates catalog retrieval to the service layer and keeps
 * transport concerns separate from data access.</p>
 */
@RestController
@RequestMapping("/api")
public class CourseControllerDb {

  private final CourseCatalogService courseCatalogService;

  public CourseControllerDb(CourseCatalogService courseCatalogService) {
    this.courseCatalogService = courseCatalogService;
  }

  /**
   * Returns the course catalog, optionally filtered by a free-text query.
   *
   * <p>The season and year parameters are accepted for frontend compatibility
   * even though this endpoint delegates only query-based filtering.</p>
   */
  // Frontend calls: /api/courses?q=Math&season=FALL&year=2026
  // season/year are accepted for compatibility; stub can ignore them.
  @GetMapping("/courses")
  public List<CourseDto> listCourses(
      @RequestParam(required = false) String q,
      @RequestParam(required = false) String season,
      @RequestParam(required = false) Integer year
  ) {
    return courseCatalogService.listCourses(q);
  }
}


