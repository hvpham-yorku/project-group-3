-- Backfill missing sections and meeting times so courses show times in UI.
-- Scope: FALL 2026 and WINTER 2027.

-- 1) Ensure every course has section A in FALL 2026
INSERT INTO sections(course_id, term_id, section_code, component, instructor, capacity, enrolled)
SELECT c.id, t.id, 'A', 'LEC', 'TBA', 120, 0
FROM courses c
JOIN terms t ON t.season = 'FALL' AND t.year = 2026
WHERE NOT EXISTS (
  SELECT 1
  FROM sections s
  WHERE s.course_id = c.id
    AND s.term_id = t.id
    AND s.section_code = 'A'
);

-- 2) Ensure every course has section A in WINTER 2027
INSERT INTO sections(course_id, term_id, section_code, component, instructor, capacity, enrolled)
SELECT c.id, t.id, 'A', 'LEC', 'TBA', 120, 0
FROM courses c
JOIN terms t ON t.season = 'WINTER' AND t.year = 2027
WHERE NOT EXISTS (
  SELECT 1
  FROM sections s
  WHERE s.course_id = c.id
    AND s.term_id = t.id
    AND s.section_code = 'A'
);

-- 3) Add FALL meetings for sections that still have none.
-- Intentionally reuses a small slot set, so overlaps naturally occur.
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT q.id AS section_id,
       CASE MOD(q.rn - 1, 5)
         WHEN 0 THEN 'MON'
         WHEN 1 THEN 'TUE'
         WHEN 2 THEN 'WED'
         WHEN 3 THEN 'THU'
         ELSE 'FRI'
       END AS day_of_week,
       CASE MOD(q.rn - 1, 6)
         WHEN 0 THEN '09:00:00'
         WHEN 1 THEN '10:00:00'
         WHEN 2 THEN '11:00:00'
         WHEN 3 THEN '13:00:00'
         WHEN 4 THEN '14:00:00'
         ELSE '15:00:00'
       END AS start_time,
       CASE MOD(q.rn - 1, 6)
         WHEN 0 THEN '10:00:00'
         WHEN 1 THEN '11:00:00'
         WHEN 2 THEN '12:00:00'
         WHEN 3 THEN '14:00:00'
         WHEN 4 THEN '15:00:00'
         ELSE '16:00:00'
       END AS end_time,
       CASE MOD(q.rn - 1, 6)
         WHEN 0 THEN 'ACE 101'
         WHEN 1 THEN 'ACE 102'
         WHEN 2 THEN 'ACE 103'
         WHEN 3 THEN 'CLH A'
         WHEN 4 THEN 'CLH B'
         ELSE 'HNE 100'
       END AS location
FROM (
  SELECT s.id, @fall_fill_rn := @fall_fill_rn + 1 AS rn
  FROM sections s
  JOIN terms t ON t.id = s.term_id
  CROSS JOIN (SELECT @fall_fill_rn := 0) vars
  WHERE t.season = 'FALL'
    AND t.year = 2026
    AND NOT EXISTS (
      SELECT 1 FROM section_meetings m WHERE m.section_id = s.id
    )
  ORDER BY s.id
) q;

-- 4) Add WINTER meetings for sections that still have none.
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT q.id AS section_id,
       CASE MOD(q.rn - 1, 5)
         WHEN 0 THEN 'MON'
         WHEN 1 THEN 'TUE'
         WHEN 2 THEN 'WED'
         WHEN 3 THEN 'THU'
         ELSE 'FRI'
       END AS day_of_week,
       CASE MOD(q.rn - 1, 6)
         WHEN 0 THEN '10:00:00'
         WHEN 1 THEN '11:00:00'
         WHEN 2 THEN '12:00:00'
         WHEN 3 THEN '14:00:00'
         WHEN 4 THEN '15:00:00'
         ELSE '16:00:00'
       END AS start_time,
       CASE MOD(q.rn - 1, 6)
         WHEN 0 THEN '11:00:00'
         WHEN 1 THEN '12:00:00'
         WHEN 2 THEN '13:00:00'
         WHEN 3 THEN '15:00:00'
         WHEN 4 THEN '16:00:00'
         ELSE '17:00:00'
       END AS end_time,
       CASE MOD(q.rn - 1, 6)
         WHEN 0 THEN 'ACE 101'
         WHEN 1 THEN 'ACE 102'
         WHEN 2 THEN 'ACE 103'
         WHEN 3 THEN 'CLH A'
         WHEN 4 THEN 'CLH B'
         ELSE 'HNE 100'
       END AS location
FROM (
  SELECT s.id, @winter_fill_rn := @winter_fill_rn + 1 AS rn
  FROM sections s
  JOIN terms t ON t.id = s.term_id
  CROSS JOIN (SELECT @winter_fill_rn := 0) vars
  WHERE t.season = 'WINTER'
    AND t.year = 2027
    AND NOT EXISTS (
      SELECT 1 FROM section_meetings m WHERE m.section_id = s.id
    )
  ORDER BY s.id
) q;
