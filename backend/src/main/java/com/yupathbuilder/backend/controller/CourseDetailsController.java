package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.dto.CourseDetailsDto;
import com.yupathbuilder.backend.store.CourseDetailsStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseDetailsController {

  private final CourseDetailsStore store;

  public CourseDetailsController(CourseDetailsStore store) {
    this.store = store;
  }

  @GetMapping("/{courseCode}/details")
  public ResponseEntity<?> details(
      @PathVariable String courseCode,
      @RequestParam String season,
      @RequestParam int year
  ) {
    CourseDetailsDto dto = store.getDetails(courseCode, season, year);
    if (dto == null) return ResponseEntity.status(404).body("Course not found: " + courseCode);
    return ResponseEntity.ok(dto);
  }
}