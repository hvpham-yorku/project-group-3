package com.yupathbuilder.backend.store;

import com.yupathbuilder.backend.dto.CourseDetailsDto;
import com.yupathbuilder.backend.dto.MeetingDto;
import com.yupathbuilder.backend.dto.SectionInfoDto;
import com.yupathbuilder.backend.model.Season;
import com.yupathbuilder.backend.repo.CourseRepo;
import com.yupathbuilder.backend.repo.SectionRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!stub")
@ConditionalOnProperty(name = "app.store", havingValue = "sql", matchIfMissing = true)
public class SqlCourseDetailsStore implements CourseDetailsStore {

  private final CourseRepo courseRepo;
  private final SectionRepo sectionRepo;

  public SqlCourseDetailsStore(CourseRepo courseRepo, SectionRepo sectionRepo) {
    this.courseRepo = courseRepo;
    this.sectionRepo = sectionRepo;
  }

  @Override
  public CourseDetailsDto getDetails(String courseCode, String season, int year) {
    var course = courseRepo.findByCourseCode(courseCode);
    if (course == null) return null;

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

    return new CourseDetailsDto(
        course.getCourseCode(),
        course.getTitle(),
        course.getDescription(),
        termString,
        sections
    );
  }
}