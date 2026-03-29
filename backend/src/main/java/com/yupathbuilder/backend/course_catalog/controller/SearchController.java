package com.yupathbuilder.backend.course_catalog.controller;

import com.yupathbuilder.backend.course_catalog.dto.CourseDto;
import com.yupathbuilder.backend.course_catalog.service.CourseCatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

  private final CourseCatalogService courseCatalogService;

  public SearchController(CourseCatalogService courseCatalogService) {
    this.courseCatalogService = courseCatalogService;
  }

  @GetMapping("/courses")
  public List<CourseDto> search(
      @RequestParam(name = "q", defaultValue = "") String q,
      @RequestParam(name = "season", required = false) String season,
      @RequestParam(name = "year", required = false) Integer year
  ) {
    return courseCatalogService.searchCourses(q);
  }
}


