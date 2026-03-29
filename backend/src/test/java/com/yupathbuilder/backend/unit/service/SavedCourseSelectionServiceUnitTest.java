package com.yupathbuilder.backend.unit.service;

import com.yupathbuilder.backend.authentication.entity.UserEntity;
import com.yupathbuilder.backend.authentication.repo.UserRepo;
import com.yupathbuilder.backend.entity.CourseEntity;
import com.yupathbuilder.backend.entity.TermEntity;
import com.yupathbuilder.backend.entity.UserSelectedCourseEntity;
import com.yupathbuilder.backend.model.Season;
import com.yupathbuilder.backend.repo.CourseRepo;
import com.yupathbuilder.backend.repo.TermRepo;
import com.yupathbuilder.backend.repo.UserSelectedCourseRepo;
import com.yupathbuilder.backend.service.SavedCourseSelectionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SavedCourseSelectionServiceUnitTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private TermRepo termRepo;

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private UserSelectedCourseRepo userSelectedCourseRepo;

    @InjectMocks
    private SavedCourseSelectionService service;

    @Test
    void listSelectionsReturnsSelectionsForUser() throws Exception {
        UserEntity user = user(11L, "wamiq@example.com");
        TermEntity fall2026 = term(1L, Season.FALL, 2026);
        CourseEntity eecs = course(3L, "EECS 1011");

        UserSelectedCourseEntity saved = new UserSelectedCourseEntity();
        saved.setUser(user);
        saved.setTerm(fall2026);
        saved.setCourse(eecs);

        given(userRepo.findByEmail("wamiq@example.com")).willReturn(Optional.of(user));
        given(userSelectedCourseRepo.findByUserIdOrderByTermYearAscTermSeasonAscCourseCourseCodeAsc(11L))
                .willReturn(List.of(saved));

        var results = service.listSelections("wamiq@example.com");

        assertEquals(1, results.size());
        assertEquals("FALL 2026", results.get(0).term());
        assertEquals("EECS 1011", results.get(0).courseCode());
    }

    @Test
    void saveSelectionRejectsCourseAlreadySavedInAnotherTerm() throws Exception {
        UserEntity user = user(11L, "wamiq@example.com");
        TermEntity winter2027 = term(2L, Season.WINTER, 2027);
        TermEntity fall2026 = term(1L, Season.FALL, 2026);
        CourseEntity eecs = course(3L, "EECS 1011");

        UserSelectedCourseEntity existing = new UserSelectedCourseEntity();
        existing.setUser(user);
        existing.setTerm(fall2026);
        existing.setCourse(eecs);

        given(userRepo.findByEmail("wamiq@example.com")).willReturn(Optional.of(user));
        given(termRepo.findBySeasonAndYear(Season.WINTER, 2027)).willReturn(winter2027);
        given(courseRepo.findByCourseCode("EECS 1011")).willReturn(eecs);
        given(userSelectedCourseRepo.findByUserIdAndCourseCourseCode(11L, "EECS 1011"))
                .willReturn(Optional.of(existing));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.saveSelection("wamiq@example.com", "WINTER 2027", "EECS 1011")
        );

        assertEquals("EECS 1011 is already saved in FALL 2026", ex.getMessage());
    }

    @Test
    void saveSelectionPersistsNewSelection() throws Exception {
        UserEntity user = user(11L, "wamiq@example.com");
        TermEntity fall2026 = term(1L, Season.FALL, 2026);
        CourseEntity eecs = course(3L, "EECS 1011");

        given(userRepo.findByEmail("wamiq@example.com")).willReturn(Optional.of(user));
        given(termRepo.findBySeasonAndYear(Season.FALL, 2026)).willReturn(fall2026);
        given(courseRepo.findByCourseCode("EECS 1011")).willReturn(eecs);
        given(userSelectedCourseRepo.findByUserIdAndCourseCourseCode(11L, "EECS 1011"))
                .willReturn(Optional.empty());
        given(userSelectedCourseRepo.save(any(UserSelectedCourseEntity.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        var result = service.saveSelection("wamiq@example.com", "FALL 2026", "EECS 1011");

        ArgumentCaptor<UserSelectedCourseEntity> captor = ArgumentCaptor.forClass(UserSelectedCourseEntity.class);
        verify(userSelectedCourseRepo).save(captor.capture());

        assertEquals(user, captor.getValue().getUser());
        assertEquals(fall2026, captor.getValue().getTerm());
        assertEquals(eecs, captor.getValue().getCourse());
        assertEquals("FALL 2026", result.term());
        assertEquals("EECS 1011", result.courseCode());
    }

    private static UserEntity user(Long id, String email) throws Exception {
        UserEntity user = new UserEntity(email, "hash", null);
        setField(user, "id", id);
        return user;
    }

    private static TermEntity term(Long id, Season season, int year) throws Exception {
        TermEntity term = new TermEntity();
        setField(term, "id", id);
        term.setSeason(season);
        term.setYear(year);
        return term;
    }

    private static CourseEntity course(Long id, String courseCode) throws Exception {
        CourseEntity course = new CourseEntity();
        setField(course, "id", id);
        course.setCourseCode(courseCode);
        course.setTitle(courseCode);
        return course;
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
