package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.dto.CourseDto;
import com.yupathbuilder.backend.store.CourseStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseControllerDb {

  private final CourseStore courseStore;

  public CourseControllerDb(CourseStore courseStore) {
    this.courseStore = courseStore;
  }

  // Frontend calls: /api/courses?q=Math&season=FALL&year=2026
  // season/year are accepted for compatibility; stub can ignore them.
  @GetMapping("/courses")
  public List<CourseDto> listCourses(
      @RequestParam(required = false) String q,
      @RequestParam(required = false) String season,
      @RequestParam(required = false) Integer year
  ) {
    var courses = (q == null || q.isBlank())
        ? courseStore.listCourses()
        : courseStore.searchCourses(q);

    return courses.stream()
        .map(c -> new CourseDto(c.getCourseCode(), c.getTitle(), c.getDescription()))
        .toList();
  }
}
