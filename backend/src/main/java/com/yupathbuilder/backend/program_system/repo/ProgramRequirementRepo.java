package com.yupathbuilder.backend.program_system.repo;

import com.yupathbuilder.backend.program_system.entity.ProgramRequirementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Persistence gateway for program requirement rows used to build checklists.
 */
public interface ProgramRequirementRepo extends JpaRepository<ProgramRequirementEntity, Long> {

    /**
     * Returns all requirement rows for a program in the order expected by the
     * checklist view.
     */
    @Query("""
        SELECT pr
        FROM ProgramRequirementEntity pr
        JOIN FETCH pr.course c
        WHERE pr.program.id = :programId
        ORDER BY pr.yearLevel, pr.groupName, pr.reqType, pr.displayOrder, c.courseCode
    """)
    List<ProgramRequirementEntity> findChecklist(Long programId);
}

