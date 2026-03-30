package com.yupathbuilder.backend.program_system.store;

import com.yupathbuilder.backend.program_system.entity.FacultyEntity;
import com.yupathbuilder.backend.program_system.entity.ProgramEntity;
import com.yupathbuilder.backend.program_system.dto.ChecklistResponseDto;

import java.util.List;

/**
 * Abstraction over faculty, program, and checklist data sources.
 */
public interface CatalogStore {
    /**
     * Returns the faculties exposed by the active catalog implementation.
     */
    List<FacultyEntity> listFaculties();

    /**
     * Returns programs for a faculty or all programs when {@code facultyId} is
     * {@code null}.
     */
    List<ProgramEntity> listPrograms(Long facultyId); // if facultyId null -> all

    /**
     * Returns the checklist for the requested program identifier.
     */
    ChecklistResponseDto checklistByProgramId(Long programId);
}

