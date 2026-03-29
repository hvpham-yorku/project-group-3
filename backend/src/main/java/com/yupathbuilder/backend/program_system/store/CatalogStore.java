package com.yupathbuilder.backend.program_system.store;

import com.yupathbuilder.backend.program_system.entity.FacultyEntity;
import com.yupathbuilder.backend.program_system.entity.ProgramEntity;
import com.yupathbuilder.backend.program_system.dto.ChecklistResponseDto;

import java.util.List;

public interface CatalogStore {
    List<FacultyEntity> listFaculties();
    List<ProgramEntity> listPrograms(Long facultyId); // if facultyId null -> all
    ChecklistResponseDto checklistByProgramId(Long programId);
}

