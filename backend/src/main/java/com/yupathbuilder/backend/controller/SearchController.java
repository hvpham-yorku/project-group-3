package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.dto.CourseDto;
import com.yupathbuilder.backend.store.CourseStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

  private final CourseStore courseStore;

  public SearchController(CourseStore courseStore) {
    this.courseStore = courseStore;
  }

  @GetMapping("/courses")
  public List<CourseDto> search(
      @RequestParam(name = "q", defaultValue = "") String q,
      @RequestParam(name = "season", required = false) String season,
      @RequestParam(name = "year", required = false) Integer year
  ) {
    if (q == null || q.trim().isEmpty()) return List.of();

    var list = courseStore.searchCourses(q);

    return list.stream()
        .map(c -> new CourseDto(c.getCourseCode(), c.getTitle(), c.getDescription()))
        .toList();
  }
}
