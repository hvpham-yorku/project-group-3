package com.yupathbuilder.backend.scheduler_system.repo;

import com.yupathbuilder.backend.scheduler_system.entity.SectionEntity;
import com.yupathbuilder.backend.scheduler_system.model.Season;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Persistence gateway for section records and their meeting schedules.
 */
public interface SectionRepo extends JpaRepository<SectionEntity, Long> {

  /**
   * Returns all sections for a course offering in a term, eagerly loading
   * meetings to avoid lazy-loading issues during schedule construction.
   */
  @Query("""
    SELECT DISTINCT s
    FROM SectionEntity s
    LEFT JOIN FETCH s.meetings m
    JOIN s.course c
    JOIN s.term t
    WHERE c.courseCode = :courseCode
      AND t.season = :season
      AND t.year = :year
    ORDER BY s.sectionCode
  """)
  List<SectionEntity> findSectionsWithMeetings(
      @Param("courseCode") String courseCode,
      @Param("season") Season season,
      @Param("year") int year
  );
}

