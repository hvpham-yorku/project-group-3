package com.yupathbuilder.backend.schedule;

import com.yupathbuilder.backend.model.Section;
import com.yupathbuilder.backend.repo.SectionRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {

    @Test
    void returnsEmptyWhenAnyCourseHasNoSectionsForTerm() {
        SectionRepository repo = mock(SectionRepository.class);
        when(repo.findByNormalizedCourseCodeAndTerm("EECS2030", "W2026")).thenReturn(List.of());
        ScheduleService service = new ScheduleService(repo);

        Optional<List<Section>> result = service.build("W2026", List.of("EECS2030"));
        assertTrue(result.isEmpty());
    }

    @Test
    void buildsNonConflictingScheduleAcrossCourses() {
        SectionRepository repo = mock(SectionRepository.class);

        // EECS2030: MW 10:30-11:45
        Section s2030 = new Section("EECS2030-A", "EECS2030", "W2026", "TBD",
                "MW", LocalTime.of(10, 30), LocalTime.of(11, 45),
                "LAS-1000", 120);

        // EECS2021: MW 15:00-16:15 (no conflict)
        Section s2021 = new Section("EECS2021-A", "EECS2021", "W2026", "TBD",
                "MW", LocalTime.of(15, 0), LocalTime.of(16, 15),
                "LAS-1000", 120);

        when(repo.findByNormalizedCourseCodeAndTerm("EECS2030", "W2026")).thenReturn(List.of(s2030));
        when(repo.findByNormalizedCourseCodeAndTerm("EECS2021", "W2026")).thenReturn(List.of(s2021));

        ScheduleService service = new ScheduleService(repo);
        Optional<List<Section>> result = service.build("W2026", List.of("EECS2030", "EECS2021"));

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        assertTrue(result.get().stream().anyMatch(s -> s.sectionId().equals("EECS2030-A")));
        assertTrue(result.get().stream().anyMatch(s -> s.sectionId().equals("EECS2021-A")));
    }

    @Test
    void backtrackingFindsAlternativeWhenFirstChoiceConflicts() {
        SectionRepository repo = mock(SectionRepository.class);

        // Course A has 2 sections: first conflicts with Course B, second doesn't
        Section a1 = new Section("A-1", "EECS2001", "W2026", "TBD",
                "MW", LocalTime.of(10, 0), LocalTime.of(11, 15),
                "LAS-1000", 120);

        Section a2 = new Section("A-2", "EECS2001", "W2026", "TBD",
                "TR", LocalTime.of(10, 0), LocalTime.of(11, 15),
                "LAS-1000", 120);

        Section b1 = new Section("B-1", "EECS2030", "W2026", "TBD",
                "MW", LocalTime.of(10, 30), LocalTime.of(11, 45),
                "LAS-1000", 120);

        when(repo.findByNormalizedCourseCodeAndTerm("EECS2001", "W2026")).thenReturn(List.of(a1, a2));
        when(repo.findByNormalizedCourseCodeAndTerm("EECS2030", "W2026")).thenReturn(List.of(b1));

        ScheduleService service = new ScheduleService(repo);
        Optional<List<Section>> result = service.build("W2026", List.of("EECS2001", "EECS2030"));

        assertTrue(result.isPresent());
        // Must pick A-2 because A-1 conflicts on MW
        assertTrue(result.get().stream().anyMatch(s -> s.sectionId().equals("A-2")));
        assertFalse(result.get().stream().anyMatch(s -> s.sectionId().equals("A-1")));
    }

    @Test
    void normalizesCourseCodes() {
        SectionRepository repo = mock(SectionRepository.class);

        Section s = new Section("EECS2030-A", "EECS2030", "W2026", "TBD",
                "MW", LocalTime.of(10, 30), LocalTime.of(11, 45),
                "LAS-1000", 120);

        when(repo.findByNormalizedCourseCodeAndTerm("EECS2030", "W2026")).thenReturn(List.of(s));

        ScheduleService service = new ScheduleService(repo);
        Optional<List<Section>> result = service.build("W2026", List.of(" eecs 2030 "));

        assertTrue(result.isPresent());
        verify(repo).findByNormalizedCourseCodeAndTerm("EECS2030", "W2026");
    }
}
