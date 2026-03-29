package com.yupathbuilder.backend.program_system.service;

import com.yupathbuilder.backend.program_system.entity.FacultyEntity;
import com.yupathbuilder.backend.program_system.entity.ProgramEntity;
import com.yupathbuilder.backend.program_system.store.CatalogStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramCatalogService {

    private final CatalogStore catalogStore;

    public ProgramCatalogService(CatalogStore catalogStore) {
        this.catalogStore = catalogStore;
    }

    public List<FacultyEntity> listFaculties() {
        return catalogStore.listFaculties();
    }

    public List<ProgramEntity> listPrograms(Long facultyId) {
        return catalogStore.listPrograms(facultyId);
    }
}
