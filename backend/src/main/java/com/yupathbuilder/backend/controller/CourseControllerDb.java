package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.dto.CourseDto;
import com.yupathbuilder.backend.repo.CourseRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseControllerDb {

  private final CourseRepo courseRepo;

  public CourseControllerDb(CourseRepo courseRepo) {
    this.courseRepo = courseRepo;
  }

  @GetMapping("/courses")
  public List<CourseDto> listCourses() {
    return courseRepo.findAll().stream()
        .map(c -> new CourseDto(c.getCourseCode(), c.getTitle()))
        .toList();
  }
}