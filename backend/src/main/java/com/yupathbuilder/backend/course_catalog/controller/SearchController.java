package com.yupathbuilder.backend.course_catalog.controller;

import com.yupathbuilder.backend.course_catalog.dto.CourseDto;
import com.yupathbuilder.backend.course_catalog.service.CourseCatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exposes the dedicated course search endpoint used by the frontend search UI.
 */
@RestController
@RequestMapping("/api/search")
public class SearchController {

  private final CourseCatalogService courseCatalogService;

  public SearchController(CourseCatalogService courseCatalogService) {
    this.courseCatalogService = courseCatalogService;
  }

  /**
   * Performs a free-text search against the course catalog.
   *
   * <p>Season and year are currently accepted for API compatibility, although
   * this endpoint only delegates query-based searching.</p>
   */
  @GetMapping("/courses")
  public List<CourseDto> search(
      @RequestParam(name = "q", defaultValue = "") String q,
      @RequestParam(name = "season", required = false) String season,
      @RequestParam(name = "year", required = false) Integer year
  ) {
    return courseCatalogService.searchCourses(q);
  }
}


