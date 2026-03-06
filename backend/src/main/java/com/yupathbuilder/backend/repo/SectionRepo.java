package com.yupathbuilder.backend.repo;

import com.yupathbuilder.backend.entity.SectionEntity;
import com.yupathbuilder.backend.model.Season;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectionRepo extends JpaRepository<SectionEntity, Long> {

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