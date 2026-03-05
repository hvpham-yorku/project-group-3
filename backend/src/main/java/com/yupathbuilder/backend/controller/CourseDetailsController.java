package com.yupathbuilder.backend.controller;

import com.yupathbuilder.backend.dto.*;
import com.yupathbuilder.backend.model.Season;
import com.yupathbuilder.backend.repo.CourseRepo;
import com.yupathbuilder.backend.repo.SectionRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseDetailsController {

  private final CourseRepo courseRepo;
  private final SectionRepo sectionRepo;

  public CourseDetailsController(CourseRepo courseRepo, SectionRepo sectionRepo) {
    this.courseRepo = courseRepo;
    this.sectionRepo = sectionRepo;
  }

  @GetMapping("/{courseCode}/details")
  public ResponseEntity<?> details(
      @PathVariable String courseCode,
      @RequestParam String season,
      @RequestParam int year
  ) {
    var course = courseRepo.findByCourseCode(courseCode);
    if (course == null) return ResponseEntity.status(404).body("Course not found: " + courseCode);

    var sec = sectionRepo.findSectionsWithMeetings(courseCode, Season.parse(season), year);

    var sections = sec.stream().map(s -> {
      var meetings = (s.getMeetings() == null ? List.<MeetingDto>of() :
          s.getMeetings().stream()
              .map(m -> new MeetingDto(
                  m.getDayOfWeek(),
                  m.getStartTime().toString(),
                  m.getEndTime().toString(),
                  m.getLocation()
              ))
              .toList()
      );
      return new SectionInfoDto(s.getSectionCode(), meetings);
    }).toList();

    var termString = Season.parse(season).name() + " " + year;

    return ResponseEntity.ok(new CourseDetailsDto(
        course.getCourseCode(),
        course.getTitle(),
        course.getDescription(),
        termString,
        sections
    ));
  }
}