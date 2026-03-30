package com.yupathbuilder.backend.program_system.repo;

import com.yupathbuilder.backend.program_system.entity.ProgramEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Persistence gateway for academic program records.
 */
public interface ProgramRepo extends JpaRepository<ProgramEntity, Long> {
    /**
     * Returns programs for a faculty ordered by display name.
     */
    List<ProgramEntity> findByFaculty_IdOrderByNameAsc(Long facultyId);

    /**
     * Resolves a program while eagerly loading its faculty relationship for
     * response mapping use cases.
     */
    @EntityGraph(attributePaths = "faculty")
    Optional<ProgramEntity> findWithFacultyById(Long id);
}


