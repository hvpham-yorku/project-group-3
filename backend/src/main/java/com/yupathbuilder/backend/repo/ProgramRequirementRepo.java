package com.yupathbuilder.backend.repo;

import com.yupathbuilder.backend.entity.ProgramRequirementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramRequirementRepo extends JpaRepository<ProgramRequirementEntity, Long> {

    @Query("""
        SELECT pr
        FROM ProgramRequirementEntity pr
        JOIN FETCH pr.course c
        WHERE pr.program.id = :programId
        ORDER BY pr.yearLevel, pr.groupName, pr.reqType, pr.displayOrder, c.courseCode
    """)
    List<ProgramRequirementEntity> findChecklist(Long programId);
}