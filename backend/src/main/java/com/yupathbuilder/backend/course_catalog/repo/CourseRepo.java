package com.yupathbuilder.backend.course_catalog.repo;

import com.yupathbuilder.backend.course_catalog.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Persistence gateway for course catalog records stored in the database.
 */
public interface CourseRepo extends JpaRepository<CourseEntity, Long> {

  /**
   * Searches courses that are offered in a specific term and match the supplied
   * text query.
   */
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
List<CourseEntity> searchByTerm(@Param("q") String q, @Param("season") com.yupathbuilder.backend.scheduler_system.model.Season season, @Param("year") int year);

  /**
   * Resolves a course by its normalized course code.
   */
  CourseEntity findByCourseCode(String courseCode);

  /**
   * Performs a lightweight case-insensitive search suitable for suggestions and
   * search previews.
   */
  List<CourseEntity> findTop20ByCourseCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(
      String code,
      String title
  );
}

