package com.yupathbuilder.backend.scheduler_system.repo;

import com.yupathbuilder.backend.scheduler_system.entity.TermEntity;
import com.yupathbuilder.backend.scheduler_system.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepo extends JpaRepository<TermEntity, Long> {
  TermEntity findBySeasonAndYear(Season season, int year);
}

