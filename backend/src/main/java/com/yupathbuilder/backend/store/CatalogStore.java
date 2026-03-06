package com.yupathbuilder.backend.store;

import com.yupathbuilder.backend.entity.FacultyEntity;
import com.yupathbuilder.backend.entity.ProgramEntity;
import com.yupathbuilder.backend.checklist.dto.ChecklistResponseDto;

import java.util.List;

public interface CatalogStore {
    List<FacultyEntity> listFaculties();
    List<ProgramEntity> listPrograms(Long facultyId); // if facultyId null -> all
    ChecklistResponseDto checklistByProgramId(Long programId);
}