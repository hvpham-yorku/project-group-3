package com.yupathbuilder.backend.scheduler_system.repo;

import com.yupathbuilder.backend.scheduler_system.entity.TermEntity;
import com.yupathbuilder.backend.scheduler_system.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Persistence gateway for academic term records.
 */
public interface TermRepo extends JpaRepository<TermEntity, Long> {
  /**
   * Resolves a term by its season and year pair.
   */
  TermEntity findBySeasonAndYear(Season season, int year);
}

