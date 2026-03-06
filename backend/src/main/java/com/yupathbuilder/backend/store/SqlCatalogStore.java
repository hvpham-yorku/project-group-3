package com.yupathbuilder.backend.store;

import com.yupathbuilder.backend.checklist.ChecklistService;
import com.yupathbuilder.backend.checklist.dto.ChecklistResponseDto;
import com.yupathbuilder.backend.entity.FacultyEntity;
import com.yupathbuilder.backend.entity.ProgramEntity;
import com.yupathbuilder.backend.repo.FacultyRepo;
import com.yupathbuilder.backend.repo.ProgramRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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

    @Override
    public List<FacultyEntity> listFaculties() {
        return facultyRepo.findAll();
    }

    @Override
    public List<ProgramEntity> listPrograms(Long facultyId) {
        if (facultyId == null) return programRepo.findAll();
        return programRepo.findByFaculty_IdOrderByNameAsc(facultyId);
    }

    @Override
    public ChecklistResponseDto checklistByProgramId(Long programId) {
        return checklistService.byProgramId(programId);
    }
}