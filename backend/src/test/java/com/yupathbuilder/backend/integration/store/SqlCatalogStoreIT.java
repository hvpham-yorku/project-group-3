package com.yupathbuilder.backend.integration.store;

import com.yupathbuilder.backend.store.CatalogStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("sql-it")
class SqlCatalogStoreIT {

    @Autowired
    private CatalogStore catalogStore;

    @Test
    void listFacultiesAndProgramsComeFromRealDatabase() {
        var faculties = catalogStore.listFaculties();
        var programs = catalogStore.listPrograms(null);

        assertTrue(faculties.stream().anyMatch(f -> "Engineering".equals(f.getName())));
        assertTrue(programs.stream().anyMatch(p -> "Software Engineering (Security Stream)".equals(p.getName())));
    }

    @Test
    void checklistByProgramIdReturnsSeededChecklist() {
        var programs = catalogStore.listPrograms(null);
        var swe = programs.stream().filter(p -> "Software Engineering (Security Stream)".equals(p.getName())).findFirst().orElseThrow();

        var checklist = catalogStore.checklistByProgramId(swe.getId());

        assertFalse(checklist.years().isEmpty());
        assertTrue(checklist.years().stream().anyMatch(y -> y.year() == 1));
        assertTrue(checklist.years().stream().flatMap(y -> y.groups().stream()).flatMap(g -> g.courses().stream()).anyMatch(c -> "EECS 1011".equals(c.courseCode())));
    }
}
