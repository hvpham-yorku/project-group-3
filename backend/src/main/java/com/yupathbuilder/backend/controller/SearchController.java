package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.dto.CourseDto;
import com.yupathbuilder.backend.model.Season;
import com.yupathbuilder.backend.repo.CourseRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

  private final CourseRepo courseRepo;

  public SearchController(CourseRepo courseRepo) {
    this.courseRepo = courseRepo;
  }

  @GetMapping("/courses")
  public List<CourseDto> search(
      @RequestParam(name="q", defaultValue="") String q,
      @RequestParam(name="season", required=false) String season,
      @RequestParam(name="year", required=false) Integer year
  ) {
    if (q == null || q.trim().isEmpty()) return List.of();

    List<com.yupathbuilder.backend.entity.CourseEntity> list;

    if (season != null && year != null) {
      list = courseRepo.searchByTerm(q, Season.parse(season), year);
    } else {
      list = courseRepo.findTop20ByCourseCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(q, q);
    }

    return list.stream().map(c -> new CourseDto(c.getCourseCode(), c.getTitle())).toList();
  }
}