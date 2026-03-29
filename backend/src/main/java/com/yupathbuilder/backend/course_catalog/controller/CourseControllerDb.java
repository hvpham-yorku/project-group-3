package com.yupathbuilder.backend.course_catalog.controller;

import com.yupathbuilder.backend.course_catalog.dto.CourseDto;
import com.yupathbuilder.backend.course_catalog.service.CourseCatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseControllerDb {

  private final CourseCatalogService courseCatalogService;

  public CourseControllerDb(CourseCatalogService courseCatalogService) {
    this.courseCatalogService = courseCatalogService;
  }

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


