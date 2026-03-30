package com.yupathbuilder.backend.program_system.store;

import com.yupathbuilder.backend.program_system.service.ChecklistService;
import com.yupathbuilder.backend.program_system.dto.ChecklistResponseDto;
import com.yupathbuilder.backend.program_system.entity.FacultyEntity;
import com.yupathbuilder.backend.program_system.entity.ProgramEntity;
import com.yupathbuilder.backend.program_system.repo.FacultyRepo;
import com.yupathbuilder.backend.program_system.repo.ProgramRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SQL-backed implementation of the program catalog store.
 */
@Component
@Profile("!stub")
@ConditionalOnProperty(name = "app.store", havingValue = "sql", matchIfMissing = true)
public class SqlCatalogStore implements CatalogStore {

    private final FacultyRepo facultyRepo;
    private final ProgramRepo programRepo;
    private final ChecklistService checklistService;

    public SqlCatalogStore(FacultyRepo facultyRepo, ProgramRepo programRepo, ChecklistService checklistService) {
        this.facultyRepo = facultyRepo;
        this.programRepo = programRepo;
        this.checklistService = checklistService;
    }

    /**
     * Returns all persisted faculties.
     */
    @Override
    public List<FacultyEntity> listFaculties() {
        return facultyRepo.findAll();
    }

    /**
     * Returns all programs or only those associated with a faculty.
     */
    @Override
    public List<ProgramEntity> listPrograms(Long facultyId) {
        if (facultyId == null) return programRepo.findAll();
        return programRepo.findByFaculty_IdOrderByNameAsc(facultyId);
    }

    /**
     * Delegates checklist generation to the SQL checklist service.
     */
    @Override
    public ChecklistResponseDto checklistByProgramId(Long programId) {
        return checklistService.byProgramId(programId);
    }
}

