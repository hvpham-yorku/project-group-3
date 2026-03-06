package com.yupathbuilder.backend.repo;

import com.yupathbuilder.backend.entity.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepo extends JpaRepository<FacultyEntity, Long> {}