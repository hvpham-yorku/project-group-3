-- Create one SUMMER 2027 section for every seeded course.
INSERT INTO sections(course_id, term_id, section_code, component, instructor, capacity, enrolled)
SELECT c.id, t.id, 'A', 'LEC', 'TBA', 120, 0
FROM courses c
JOIN terms t ON t.season = 'SUMMER' AND t.year = 2027
WHERE NOT EXISTS (
  SELECT 1
  FROM sections s
  WHERE s.course_id = c.id
    AND s.term_id = t.id
    AND s.section_code = 'A'
);

-- Reuse the FALL 2026 meeting pattern for SUMMER 2027 so every course is schedulable.
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT summer.id, fallMeetings.day_of_week, fallMeetings.start_time, fallMeetings.end_time, fallMeetings.location
FROM sections summer
JOIN courses c ON c.id = summer.course_id
JOIN terms summerTerm ON summerTerm.id = summer.term_id
JOIN sections fall ON fall.course_id = c.id AND fall.section_code = 'A'
JOIN terms fallTerm ON fallTerm.id = fall.term_id AND fallTerm.season = 'FALL' AND fallTerm.year = 2026
JOIN section_meetings fallMeetings ON fallMeetings.section_id = fall.id
WHERE summerTerm.season = 'SUMMER'
  AND summerTerm.year = 2027
  AND summer.section_code = 'A'
  AND NOT EXISTS (
    SELECT 1
    FROM section_meetings existing
    WHERE existing.section_id = summer.id
      AND existing.day_of_week = fallMeetings.day_of_week
      AND existing.start_time = fallMeetings.start_time
  );
