-- V2__seed_itr2_programs_courses.sql
-- Adds two programs (Software Eng Security Stream + Kinesiology) and
-- adds 2 courses per year for each program (total 16 courses).
-- Also adds 1 section per course per term (FALL 2026 + WINTER 2027) with NO conflicts.

-- ---------------------------------------------------------
-- 0) Ensure terms exist
-- ---------------------------------------------------------
INSERT INTO terms(season, year) VALUES ('FALL', 2026)
ON DUPLICATE KEY UPDATE year = year;

INSERT INTO terms(season, year) VALUES ('WINTER', 2027)
ON DUPLICATE KEY UPDATE year = year;

INSERT INTO faculties(name) VALUES ('Engineering')
ON DUPLICATE KEY UPDATE name = name;


-- ---------------------------------------------------------
-- 2) Programs
-- ---------------------------------------------------------
-- Software Engineering (Security Stream)
INSERT INTO programs(faculty_id, name, degree)
SELECT f.id, 'Software Engineering (Security Stream)', 'BEng'
FROM faculties f
WHERE f.name = 'Engineering'
AND NOT EXISTS (
  SELECT 1 FROM programs p WHERE p.name = 'Software Engineering (Security Stream)'
);


-- ---------------------------------------------------------
-- 3) Courses (2 per year per program)
-- ---------------------------------------------------------
-- Software Engineering (Security Stream) — 8 courses total
INSERT INTO courses(course_code, subject, catalog_number, title, description)
VALUES
('EECS 1011', 'EECS', '1011', 'Computational Thinking Through Mechatronics', 'Intro computational thinking (selected for Year 1 demo).'),
('MATH 1013', 'MATH', '1013', 'Applied Calculus I', 'Limits, derivatives, and applications in engineering (selected for Year 1 demo).'),

('EECS 2030', 'EECS', '2030', 'Advanced Object Oriented Programming', 'Object-oriented design and programming (selected for Year 2 demo).'),
('EECS 2311', 'EECS', '2311', 'Software Development Project', 'Project-based software engineering course (selected for Year 2 demo).'),

('EECS 3311', 'EECS', '3311', 'Software Design', 'Design patterns, architecture, and maintainability (selected for Year 3 demo).'),
('EECS 3482', 'EECS', '3482', 'Introduction to Computer Security', 'Security foundations and threats (selected for Year 3 demo).'),

('EECS 4481', 'EECS', '4481', 'Computer Security Laboratory', 'Hands-on security lab work (selected for Year 4 demo).'),
('EECS 4482', 'EECS', '4482', 'Computer Security Management: Assessment and Forensics', 'Security assessment and forensics (selected for Year 4 demo).'),
-- Missing Software Engineering (Security Stream) courses

('EECS 3101', 'EECS', '3101', 'Design and Analysis of Algorithms', 'Core algorithms course required in Security Stream.'),
('EECS 3201', 'EECS', '3201', 'Digital Logic Design', 'Fundamentals of digital logic design.'),
('EECS 3214', 'EECS', '3214', 'Computer Network Protocols and Applications', 'Networks and protocols — part of security specialization.'),
('EECS 3221', 'EECS', '3221', 'Operating System Fundamentals', 'Introductory operating systems course.'),
('EECS 3342', 'EECS', '3342', 'System Specification and Refinement', 'Software systems specification and refinement.'),
('LE/ENG 3000', 'ENG', '3000', 'Professional Engineering Practice', 'Professional practice and ethics for engineers.'),

-- Fourth Year / Advanced Software Engineering
('EECS 3216', 'EECS', '3216', 'Digital Systems Engineering: Modeling, Implementation and Validation', 'Advanced digital systems engineering.'),
('EECS 3481', 'EECS', '3481', 'Applied Cryptography', 'Applied cryptography principles — required for security focus.'),
('EECS 4312', 'EECS', '4312', 'Software Engineering Requirements', 'Software requirements engineering.'),
('EECS 4313', 'EECS', '4313', 'Software Engineering Testing', 'Testing methods and QA in software engineering.'),
('EECS 4314', 'EECS', '4314', 'Advanced Software Engineering', 'Advanced topics in software development.'),
('EECS 4315', 'EECS', '4315', 'Mission-Critical Systems', 'Designing and building mission-critical systems.'),
('EECS 4400', 'EECS', '4000', 'Engineering Project', 'Year‑long engineering capstone project.'),

('EECS 4413', 'EECS', '4413', 'Building E-Commerce Systems', 'Advanced systems development (required elective).')
ON DUPLICATE KEY UPDATE
title = VALUES(title),
description = VALUES(description);

-- ---------------------------------------------------------
-- 4) Program checklist requirements (2 courses per year)
-- ---------------------------------------------------------
-- Helper note: year_level in schema is TINYINT. Keep year values 1..4.

-- SWE Security Stream program_id
-- Year 1
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 1011'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=1 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='MATH 1013'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=1 AND r.course_id=c.id
);
-- Year 1 additional courses
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'REQUIRED', 'Core', 3
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 1520'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=1 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'REQUIRED', 'Core', 4
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 1540'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=1 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'REQUIRED', 'Core', 5
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 1570'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=1 AND r.course_id=c.id
);

-- Year 2
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 2030'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=2 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 2311'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=2 AND r.course_id=c.id
);

-- Year 2 additional courses
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'REQUIRED', 'Core', 3
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 2030'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=2 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'REQUIRED', 'Core', 4
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 2311'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=2 AND r.course_id=c.id
);

-- Year 3
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 3311'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=3 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 3482'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=3 AND r.course_id=c.id
);

-- Year 3 additional courses
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 3311'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=3 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 3482'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=3 AND r.course_id=c.id
);

-- Year 4
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 4, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 4481'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=4 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 4, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 4482'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=4 AND r.course_id=c.id
);
-- Year 4 additional courses
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 4, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 4481'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=4 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 4, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Software Engineering (Security Stream)' AND c.course_code='EECS 4482'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=4 AND r.course_id=c.id
);

-- ---------------------------------------------------------
-- 5) Sections + Meetings (1 section per course per term)
-- NO conflicts within the same term.
-- ---------------------------------------------------------

-- Helper: create one section A for each course in FALL 2026 with unique time slots.
-- FALL slots (no overlap):
-- 09:30-10:30 MW, 10:30-11:30 MW, 12:30-13:30 MW, 14:30-15:30 MW,
-- 09:30-10:30 TTh, 10:30-11:30 TTh, 12:30-13:30 TTh, 14:30-15:30 TTh,
-- 16:00-17:00 F, 17:00-18:00 F, etc.

-- Create FALL sections 
INSERT INTO sections(course_id, term_id, section_code, component, instructor, capacity, enrolled)
SELECT c.id, t.id, 'A', 'LEC', 'TBA', 120, 0
FROM courses c
JOIN terms t ON t.season='FALL' AND t.year=2026
WHERE c.course_code IN (
 'EECS 1011','MATH 1013','EECS 2030','EECS 2311','EECS 3311','EECS 3482','EECS 4481','EECS 4482',
'KINE 1000','KINE 1020','KINE 2011','KINE 2031','KINE 3012','KINE 3030','KINE 4010','KINE 4020',
'BIOL 1000','BIOL 1001','CHEM 1000','CHEM 1001','PHYS 1410','PHYS 1420','PHYS 1411','PHYS 1421',
'PHYS 1412','PHYS 1422','HUMA 1105','SOSC 1009','MATH 1506','MATH 1507','MATH 1505','MATH 1014',
'MATH 1025','EECS 1520','EECS 1540','EECS 1570','KINE 2049','KINE 2050','KINE 3000','KINE 3020',
'KINE 3440','KINE 3620','KINE 3400','KINE 3100','PSYC 1010','BIOL 2040','MATH 1510','PKIN 0570',
'PKIN 0295','PKIN 0303','PKIN 0440','PKIN 0811','PKIN 0812','PKIN 0840','PKIN 0861','PKIN 0862',
'PKIN 0200','PKIN 0240','PKIN 0270','PKIN 0751','PKIN 0750','PKIN 0770','PKIN 0600','PKIN 0610',
'EECS 3101','EECS 3201','EECS 3214','EECS 3221','EECS 3342','LE/ENG 3000','EECS 3216','EECS 3481',
'EECS 4312','EECS 4313','EECS 4314','EECS 4315','EECS 4400','EECS 4413'
)
AND NOT EXISTS (
  SELECT 1 FROM sections s WHERE s.course_id=c.id AND s.term_id=t.id AND s.section_code='A'
);

-- Create WINTER sections 
INSERT INTO sections(course_id, term_id, section_code, component, instructor, capacity, enrolled)
SELECT c.id, t.id, 'A', 'LEC', 'TBA', 120, 0
FROM courses c
JOIN terms t ON t.season='WINTER' AND t.year=2027
WHERE c.course_code IN (
  'EECS 1011','MATH 1013','EECS 2030','EECS 2311','EECS 3311','EECS 3482','EECS 4481','EECS 4482',
'KINE 1000','KINE 1020','KINE 2011','KINE 2031','KINE 3012','KINE 3030','KINE 4010','KINE 4020',
'BIOL 1000','BIOL 1001','CHEM 1000','CHEM 1001','PHYS 1410','PHYS 1420','PHYS 1411','PHYS 1421',
'PHYS 1412','PHYS 1422','HUMA 1105','SOSC 1009','MATH 1506','MATH 1507','MATH 1505','MATH 1014',
'MATH 1025','EECS 1520','EECS 1540','EECS 1570','KINE 2049','KINE 2050','KINE 3000','KINE 3020',
'KINE 3440','KINE 3620','KINE 3400','KINE 3100','PSYC 1010','BIOL 2040','MATH 1510','PKIN 0570',
'PKIN 0295','PKIN 0303','PKIN 0440','PKIN 0811','PKIN 0812','PKIN 0840','PKIN 0861','PKIN 0862',
'PKIN 0200','PKIN 0240','PKIN 0270','PKIN 0751','PKIN 0750','PKIN 0770','PKIN 0600','PKIN 0610',
'EECS 3101','EECS 3201','EECS 3214','EECS 3221','EECS 3342','LE/ENG 3000','EECS 3216','EECS 3481',
'EECS 4312','EECS 4313','EECS 4314','EECS 4315','EECS 4400','EECS 4413'
)
AND NOT EXISTS (
  SELECT 1 FROM sections s WHERE s.course_id=c.id AND s.term_id=t.id AND s.section_code='A'
);

-- ---------------------------------------------------------
-- Meeting assignment (FALL 2026) — unique slots per course
-- ---------------------------------------------------------
-- We assign each course a distinct slot (no conflicts). Locations are realistic-ish.

-- FALL: Software Engineering (8 courses)
-- EECS 1011  MW 09:30-10:30  LAS 1004
-- MATH 1013  MW 10:30-11:30  LAS 1004
-- EECS 2030  MW 12:30-13:30  ACW 109
-- EECS 2311  MW 14:30-15:30  ACW 109
-- EECS 3311  TTh 09:30-10:30 CLH 201
-- EECS 3482  TTh 10:30-11:30 CLH 201
-- EECS 4481  TTh 12:30-13:30 LAS 1004
-- EECS 4482  TTh 14:30-15:30 LAS 1004

-- FALL: Kinesiology (8 courses)
-- KINE 1000  F   09:30-10:30 HNE 100
-- KINE 1020  F   10:30-11:30 HNE 100
-- KINE 2011  F   12:30-13:30 HNE 200
-- KINE 2031  F   14:30-15:30 HNE 200
-- KINE 3012  M   16:00-17:00 HNE 300
-- KINE 3030  W   16:00-17:00 HNE 300
-- KINE 4010  T   16:00-17:00 HNE 400
-- KINE 4020  Th  16:00-17:00 HNE 400

-- Helper insert template: insert meeting if not exists
-- (We do explicit inserts for each course + day.)

-- ========== FALL inserts (MW courses) ==========
-- Realistic rooms list
SET @rooms = 'LAS 1004,ACW 109,CLH 201,LAS 1004,HNE 100,HNE 200,HNE 300,HNE 400';

-- Split the rooms into a temporary table
DROP TEMPORARY TABLE IF EXISTS temp_rooms;
CREATE TEMPORARY TABLE temp_rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    room_name VARCHAR(20)
);

-- Populate temp_rooms
INSERT INTO temp_rooms(room_name)
SELECT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(@rooms, ',', n.n), ',', -1))
FROM (
    SELECT 1 AS n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
    UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8
) n;

-- Insert section meetings for FALL 2026 with realistic rooms
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT q.id AS section_id,
       CASE MOD(q.rn - 1, 5)
           WHEN 0 THEN 'MON'
           WHEN 1 THEN 'TUE'
           WHEN 2 THEN 'WED'
           WHEN 3 THEN 'THU'
           ELSE 'FRI'
       END AS day_of_week,
       SEC_TO_TIME((8 + MOD(q.rn - 1, 8)) * 3600) AS start_time,
       SEC_TO_TIME((9 + MOD(q.rn - 1, 8)) * 3600 + 1800) AS end_time,
       CASE MOD(q.rn - 1, 8)
           WHEN 0 THEN 'LAS 1004'
           WHEN 1 THEN 'ACW 109'
           WHEN 2 THEN 'CLH 201'
           WHEN 3 THEN 'LAS 1004'
           WHEN 4 THEN 'HNE 100'
           WHEN 5 THEN 'HNE 200'
           WHEN 6 THEN 'HNE 300'
           ELSE 'HNE 400'
       END AS location
FROM (
    SELECT s.id, @fall_rn := @fall_rn + 1 AS rn
    FROM sections s
    JOIN terms t ON t.id = s.term_id
    CROSS JOIN (SELECT @fall_rn := 0) vars
    WHERE t.season='FALL' AND t.year=2026
      AND NOT EXISTS (
          SELECT 1 FROM section_meetings m WHERE m.section_id = s.id
      )
    ORDER BY s.id
) q;
-- ---------------------------------------------------------
-- WINTER 2027 meetings: shift everything to different slots (still no conflicts)
-- (We keep same pairing pattern but different days/rooms so term change is visible.)
-- ---------------------------------------------------------
-- WINTER: SWE courses
-- EECS 1011  TTh 09:30-10:30  LAS 1004
-- MATH 1013  TTh 10:30-11:30  LAS 1004
-- EECS 2030  TTh 12:30-13:30  ACW 109
-- EECS 2311  TTh 14:30-15:30  ACW 109
-- EECS 3311  MW 09:30-10:30   CLH 201
-- EECS 3482  MW 10:30-11:30   CLH 201
-- EECS 4481  MW 12:30-13:30   LAS 1004
-- EECS 4482  MW 14:30-15:30   LAS 1004
--
-- WINTER: KINE courses
-- KINE 1000  F   09:30-10:30  HNE 100
-- KINE 1020  F   10:30-11:30  HNE 100
-- KINE 2011  F   12:30-13:30  HNE 200
-- KINE 2031  F   14:30-15:30  HNE 200
-- KINE 3012  MON 16:00-17:00  HNE 300
-- KINE 3030  WED 16:00-17:00  HNE 300
-- KINE 4010  TUE 16:00-17:00  HNE 400
-- KINE 4020  THU 16:00-17:00  HNE 400

-- (For WINTER, same pattern as FALL but swapped MW <-> TTh for SWE.)
-- ---------------------------------------------------------
-- Meeting assignment (WINTER 2027) — unique slots per course
-- ---------------------------------------------------------

-- Realistic rooms list (reuse same as FALL)
SET @rooms = 'LAS 1004,ACW 109,CLH 201,LAS 1004,HNE 100,HNE 200,HNE 300,HNE 400';

-- Drop temp table if exists
DROP TEMPORARY TABLE IF EXISTS temp_rooms;
CREATE TEMPORARY TABLE temp_rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    room_name VARCHAR(20)
);

-- Populate temp_rooms
INSERT INTO temp_rooms(room_name)
SELECT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(@rooms, ',', n.n), ',', -1))
FROM (
    SELECT 1 AS n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
    UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8
) n;

-- Insert section meetings for WINTER 2027 with realistic rooms
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT q.id AS section_id,
       CASE MOD(q.rn - 1, 5)
           WHEN 0 THEN 'MON'
           WHEN 1 THEN 'TUE'
           WHEN 2 THEN 'WED'
           WHEN 3 THEN 'THU'
           ELSE 'FRI'
       END AS day_of_week,
       SEC_TO_TIME((8 + MOD(q.rn - 1, 8)) * 3600) AS start_time,
       SEC_TO_TIME((9 + MOD(q.rn - 1, 8)) * 3600 + 1800) AS end_time,
       CASE MOD(q.rn - 1, 8)
           WHEN 0 THEN 'LAS 1004'
           WHEN 1 THEN 'ACW 109'
           WHEN 2 THEN 'CLH 201'
           WHEN 3 THEN 'LAS 1004'
           WHEN 4 THEN 'HNE 100'
           WHEN 5 THEN 'HNE 200'
           WHEN 6 THEN 'HNE 300'
           ELSE 'HNE 400'
       END AS location
FROM (
    SELECT s.id, @winter_rn := @winter_rn + 1 AS rn
    FROM sections s
    JOIN terms t ON t.id = s.term_id
    CROSS JOIN (SELECT @winter_rn := 0) vars
    WHERE t.season='WINTER' AND t.year=2027
      AND NOT EXISTS (
          SELECT 1 FROM section_meetings m WHERE m.section_id = s.id
      )
    ORDER BY s.id
) q;
