package com.yupathbuilder.backend.unit.service;

import com.yupathbuilder.backend.course_catalog.entity.CourseEntity;
import com.yupathbuilder.backend.scheduler_system.entity.SectionEntity;
import com.yupathbuilder.backend.scheduler_system.entity.SectionMeetingEntity;
import com.yupathbuilder.backend.scheduler_system.entity.TermEntity;
import com.yupathbuilder.backend.scheduler_system.model.Season;
import com.yupathbuilder.backend.scheduler_system.repo.SectionRepo;
import com.yupathbuilder.backend.scheduler_system.repo.TermRepo;
import com.yupathbuilder.backend.scheduler_system.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceUnitTest {

    @Mock
    private TermRepo termRepo;

    @Mock
    private SectionRepo sectionRepo;

    @InjectMocks
    private ScheduleService service;

    @Test
    void buildReturnsOneChosenSectionPerCourseWhenNoConflicts() throws Exception {
        TermEntity fall2026 = term(Season.FALL, 2026);
        given(termRepo.findBySeasonAndYear(Season.FALL, 2026)).willReturn(fall2026);
        given(sectionRepo.findSectionsWithMeetings("EECS 1011", Season.FALL, 2026)).willReturn(List.of(
                section("EECS 1011", "A", meeting("MON", "09:30", "10:30", "LAS 1004"), meeting("WED", "09:30", "10:30", "LAS 1004"))
        ));
        given(sectionRepo.findSectionsWithMeetings("MATH 1013", Season.FALL, 2026)).willReturn(List.of(
                section("MATH 1013", "A", meeting("MON", "10:30", "11:30", "LAS 1004"), meeting("WED", "10:30", "11:30", "LAS 1004"))
        ));

        var response = service.build("FALL 2026", List.of("EECS 1011", "MATH 1013"));

        assertEquals(2, response.chosenSections().size());
        assertEquals("EECS 1011", response.chosenSections().get(0).courseCode());
        assertEquals("MON,WED", response.chosenSections().get(0).days());
    }

    @Test
    void buildSkipsBlankCourseCodes() throws Exception {
        TermEntity fall2026 = term(Season.FALL, 2026);
        given(termRepo.findBySeasonAndYear(Season.FALL, 2026)).willReturn(fall2026);
        given(sectionRepo.findSectionsWithMeetings("EECS 1011", Season.FALL, 2026)).willReturn(List.of(
                section("EECS 1011", "A", meeting("MON", "09:30", "10:30", "LAS 1004"))
        ));

        var response = service.build("FALL 2026", Arrays.asList("EECS 1011", " ", null));

        assertEquals(1, response.chosenSections().size());
    }

    @Test
    void buildRejectsUnknownTerm() {
        given(termRepo.findBySeasonAndYear(Season.FALL, 2026)).willReturn(null);

        assertThrows(IllegalArgumentException.class, () -> service.build("FALL 2026", List.of("EECS 1011")));
    }

    @Test
    void buildRejectsCourseWithNoSections() throws Exception {
        TermEntity fall2026 = term(Season.FALL, 2026);
        given(termRepo.findBySeasonAndYear(Season.FALL, 2026)).willReturn(fall2026);
        given(sectionRepo.findSectionsWithMeetings("EECS 1011", Season.FALL, 2026)).willReturn(List.of());

        assertThrows(IllegalArgumentException.class, () -> service.build("FALL 2026", List.of("EECS 1011")));
    }

    @Test
    void buildChoosesFirstNonConflictingSection() throws Exception {
        TermEntity fall2026 = term(Season.FALL, 2026);
        given(termRepo.findBySeasonAndYear(Season.FALL, 2026)).willReturn(fall2026);
        given(sectionRepo.findSectionsWithMeetings("EECS 1011", Season.FALL, 2026)).willReturn(List.of(
                section("EECS 1011", "A", meeting("MON", "09:30", "10:30", "LAS 1004"))
        ));
        given(sectionRepo.findSectionsWithMeetings("MATH 1013", Season.FALL, 2026)).willReturn(List.of(
                section("MATH 1013", "A", meeting("MON", "10:00", "11:00", "LAS 1004")),
                section("MATH 1013", "B", meeting("MON", "11:00", "12:00", "LAS 1004"))
        ));

        var response = service.build("FALL 2026", List.of("EECS 1011", "MATH 1013"));

        assertEquals("B", response.chosenSections().get(1).sectionId());
    }

    @Test
    void buildRejectsWhenAllSectionsConflict() throws Exception {
        TermEntity fall2026 = term(Season.FALL, 2026);
        given(termRepo.findBySeasonAndYear(Season.FALL, 2026)).willReturn(fall2026);
        given(sectionRepo.findSectionsWithMeetings("EECS 1011", Season.FALL, 2026)).willReturn(List.of(
                section("EECS 1011", "A", meeting("MON", "09:30", "10:30", "LAS 1004"))
        ));
        given(sectionRepo.findSectionsWithMeetings("MATH 1013", Season.FALL, 2026)).willReturn(List.of(
                section("MATH 1013", "A", meeting("MON", "10:00", "11:00", "LAS 1004"))
        ));

        assertThrows(IllegalArgumentException.class, () -> service.build("FALL 2026", List.of("EECS 1011", "MATH 1013")));
    }

    private static TermEntity term(Season season, int year) {
        TermEntity t = new TermEntity();
        t.setSeason(season);
        t.setYear(year);
        return t;
    }

    private static SectionEntity section(String courseCode, String sectionCode, SectionMeetingEntity... meetings) throws Exception {
        SectionEntity section = new SectionEntity();
        CourseEntity course = new CourseEntity();
        course.setCourseCode(courseCode);
        setField(section, "course", course);
        setField(section, "sectionCode", sectionCode);
        setField(section, "meetings", List.of(meetings));
        return section;
    }

    private static SectionMeetingEntity meeting(String day, String start, String end, String location) throws Exception {
        SectionMeetingEntity meeting = new SectionMeetingEntity();
        setField(meeting, "dayOfWeek", day);
        setField(meeting, "startTime", LocalTime.parse(start));
        setField(meeting, "endTime", LocalTime.parse(end));
        setField(meeting, "location", location);
        return meeting;
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
