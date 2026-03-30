package com.yupathbuilder.backend.program_system.service;

import com.yupathbuilder.backend.program_system.entity.FacultyEntity;
import com.yupathbuilder.backend.program_system.entity.ProgramEntity;
import com.yupathbuilder.backend.program_system.store.CatalogStore;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Coordinates faculty and program catalog lookups.
 *
 * <p>This service delegates to the active catalog store so controllers do not
 * need to know whether data is coming from SQL or stub resources.</p>
 */
@Service
public class ProgramCatalogService {

    private final CatalogStore catalogStore;

    public ProgramCatalogService(CatalogStore catalogStore) {
        this.catalogStore = catalogStore;
    }

    /**
     * Returns all faculties from the active catalog source.
     */
    public List<FacultyEntity> listFaculties() {
        return catalogStore.listFaculties();
    }

    /**
     * Returns all programs or only those belonging to the requested faculty.
     */
    public List<ProgramEntity> listPrograms(Long facultyId) {
        return catalogStore.listPrograms(facultyId);
    }
}
