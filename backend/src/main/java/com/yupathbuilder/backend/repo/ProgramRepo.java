package com.yupathbuilder.backend.repo;

import com.yupathbuilder.backend.entity.ProgramEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgramRepo extends JpaRepository<ProgramEntity, Long> {
    List<ProgramEntity> findByFaculty_IdOrderByNameAsc(Long facultyId);

    @EntityGraph(attributePaths = "faculty")
    Optional<ProgramEntity> findWithFacultyById(Long id);
}
