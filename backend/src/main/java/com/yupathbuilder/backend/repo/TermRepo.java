package com.yupathbuilder.backend.repo;

import com.yupathbuilder.backend.entity.TermEntity;
import com.yupathbuilder.backend.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepo extends JpaRepository<TermEntity, Long> {
  TermEntity findBySeasonAndYear(Season season, int year);
}