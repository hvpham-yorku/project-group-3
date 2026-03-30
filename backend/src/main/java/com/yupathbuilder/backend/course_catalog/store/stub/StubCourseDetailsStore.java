package com.yupathbuilder.backend.course_catalog.store.stub;

import com.yupathbuilder.backend.course_catalog.dto.CourseDetailsDto;
import com.yupathbuilder.backend.course_catalog.dto.SectionInfoDto;
import com.yupathbuilder.backend.course_catalog.entity.CourseEntity;
import com.yupathbuilder.backend.course_catalog.store.CourseDetailsStore;
import com.yupathbuilder.backend.course_catalog.store.CourseStore;
import com.yupathbuilder.backend.scheduler_system.store.stub.StubTimeRules;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Stub-backed course details store that synthesizes deterministic section data
 * for local development and demos.
 */
@Component
@ConditionalOnProperty(name = "app.store", havingValue = "stub")
public class StubCourseDetailsStore implements CourseDetailsStore {

  private final CourseStore courseStore;

  public StubCourseDetailsStore(CourseStore courseStore) {
    this.courseStore = courseStore;
  }

  /**
   * Builds a stable course-details response from stub catalog data.
   */
  @Override
  public CourseDetailsDto getDetails(String courseCode, String season, int year) {
    CourseEntity c = courseStore.listCourses().stream()
        .filter(x -> x.getCourseCode() != null && x.getCourseCode().equalsIgnoreCase(courseCode))
        .findFirst()
        .orElse(null);

    if (c == null) return null;

    // Fake, deterministic-ish schedule so it looks realistic
//    boolean winter = season != null && season.equalsIgnoreCase("WINTER");

// var meetings = winter
//     ? List.of(
//         new MeetingDto("TUE", "14:30", "15:30", "ACW 109"),
//         new MeetingDto("THU", "14:30", "15:30", "ACW 109")
//       )
//     : List.of(
//         new MeetingDto("MON", "10:30", "11:30", "LAS 1004"),
//         new MeetingDto("WED", "10:30", "11:30", "LAS 1004")
//       );

// StubTimeRules keeps generated schedules deterministic across requests.
var slot = StubTimeRules.slotFor(c.getCourseCode(), season);

var sections = List.of(
    new SectionInfoDto(slot.section(), slot.meetings())
);

    String term = season.toUpperCase() + " " + year;

    return new CourseDetailsDto(
    c.getCourseCode(),
    c.getTitle(),
    c.getDescription(),
    term,
    sections
);
  }
}

