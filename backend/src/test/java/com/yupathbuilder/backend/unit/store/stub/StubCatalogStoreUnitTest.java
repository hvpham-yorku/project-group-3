package com.yupathbuilder.backend.unit.store.stub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupathbuilder.backend.store.stub.StubCatalogStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StubCatalogStoreUnitTest {

    private final StubCatalogStore store = new StubCatalogStore(new ObjectMapper());

    @Test
    void listFacultiesLoadsExpectedDefaults() {
        var faculties = store.listFaculties();

        assertEquals(2, faculties.size());
        assertTrue(faculties.stream().anyMatch(f -> "Engineering".equals(f.getName())));
        assertTrue(faculties.stream().anyMatch(f -> "Health".equals(f.getName())));
    }

    @Test
    void listProgramsFiltersByFacultyId() {
        var engineeringPrograms = store.listPrograms(1L);

        assertEquals(1, engineeringPrograms.size());
        assertEquals("Software Engineering (Security Stream)", engineeringPrograms.get(0).getName());
    }

    @Test
    void checklistByProgramIdReturnsEmptyChecklistWhenProgramDoesNotExist() {
        var checklist = store.checklistByProgramId(999L);

        assertEquals(999L, checklist.programId());
        assertTrue(checklist.years().isEmpty());
    }
}
