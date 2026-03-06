package com.yupathbuilder.backend.repo;

import com.yupathbuilder.backend.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepo extends JpaRepository<CourseEntity, Long> {

  @Query("""
  SELECT DISTINCT c
  FROM CourseEntity c
  JOIN SectionEntity s ON s.course = c
  JOIN TermEntity t ON s.term = t
  WHERE (LOWER(c.courseCode) LIKE LOWER(CONCAT('%', :q, '%'))
      OR LOWER(c.title) LIKE LOWER(CONCAT('%', :q, '%')))
    AND t.season = :season
    AND t.year = :year
  ORDER BY c.courseCode
""")
List<CourseEntity> searchByTerm(@Param("q") String q, @Param("season") com.yupathbuilder.backend.model.Season season, @Param("year") int year);

  CourseEntity findByCourseCode(String courseCode);

  List<CourseEntity> findTop20ByCourseCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(
      String code,
      String title
  );
}