-- V3__seed_itr2_programs_courses.sql
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

-- ---------------------------------------------------------
-- 1) Faculties
-- ---------------------------------------------------------
INSERT INTO faculties(name) VALUES ('Engineering')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO faculties(name) VALUES ('Health')
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

-- Kinesiology
INSERT INTO programs(faculty_id, name, degree)
SELECT f.id, 'Kinesiology', 'BSc'
FROM faculties f
WHERE f.name = 'Health'
AND NOT EXISTS (
  SELECT 1 FROM programs p WHERE p.name = 'Kinesiology'
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
('EECS 4482', 'EECS', '4482', 'Computer Security Management: Assessment and Forensics', 'Security assessment and forensics (selected for Year 4 demo).')
ON DUPLICATE KEY UPDATE
title = VALUES(title),
description = VALUES(description);

-- Kinesiology — 8 courses total (from your CSV)
INSERT INTO courses(course_code, subject, catalog_number, title, description)
VALUES
('KINE 1000', 'KINE', '1000', 'Kinesiology: Sociocultural Perspectives', 'Foundations of kinesiology (Year 1 demo).'),
('KINE 1020', 'KINE', '1020', 'Fitness and Health', 'Fitness, health and training basics (Year 1 demo).'),

('KINE 2011', 'KINE', '2011', 'Human Physiology I', 'Intro human physiology (Year 2 demo).'),
('KINE 2031', 'KINE', '2031', 'Human Anatomy', 'Intro human anatomy (Year 2 demo).'),

('KINE 3012', 'KINE', '3012', 'Human Physiology II', 'Advanced physiology (Year 3 demo).'),
('KINE 3030', 'KINE', '3030', 'Biomechanics of Human Movement', 'Biomechanics fundamentals (Year 3 demo).'),

('KINE 4010', 'KINE', '4010', 'Exercise Physiology', 'Exercise physiology (Year 4 demo).'),
('KINE 4020', 'KINE', '4020', 'Human Nutrition', 'Human nutrition (Year 4 demo).')
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

-- Kinesiology program_id
-- Year 1
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 1000'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=1 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 1020'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=1 AND r.course_id=c.id
);

-- Year 2
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 2011'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=2 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 2031'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=2 AND r.course_id=c.id
);

-- Year 3
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 3012'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=3 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 3030'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=3 AND r.course_id=c.id
);

-- Year 4
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 4, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 4010'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=4 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 4, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 4020'
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

-- Create FALL sections (A) for all 16 courses
INSERT INTO sections(course_id, term_id, section_code, component, instructor, capacity, enrolled)
SELECT c.id, t.id, 'A', 'LEC', 'TBA', 120, 0
FROM courses c
JOIN terms t ON t.season='FALL' AND t.year=2026
WHERE c.course_code IN (
  'EECS 1011','MATH 1013','EECS 2030','EECS 2311','EECS 3311','EECS 3482','EECS 4481','EECS 4482',
  'KINE 1000','KINE 1020','KINE 2011','KINE 2031','KINE 3012','KINE 3030','KINE 4010','KINE 4020'
)
AND NOT EXISTS (
  SELECT 1 FROM sections s WHERE s.course_id=c.id AND s.term_id=t.id AND s.section_code='A'
);

-- Create WINTER sections (A) for all 16 courses
INSERT INTO sections(course_id, term_id, section_code, component, instructor, capacity, enrolled)
SELECT c.id, t.id, 'A', 'LEC', 'TBA', 120, 0
FROM courses c
JOIN terms t ON t.season='WINTER' AND t.year=2027
WHERE c.course_code IN (
  'EECS 1011','MATH 1013','EECS 2030','EECS 2311','EECS 3311','EECS 3482','EECS 4481','EECS 4482',
  'KINE 1000','KINE 1020','KINE 2011','KINE 2031','KINE 3012','KINE 3030','KINE 4010','KINE 4020'
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
-- EECS 1011
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON','09:30:00','10:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 1011' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='09:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED','09:30:00','10:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 1011' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='09:30:00');

-- MATH 1013
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON','10:30:00','11:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='MATH 1013' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='10:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED','10:30:00','11:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='MATH 1013' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='10:30:00');

-- EECS 2030
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON','12:30:00','13:30:00','ACW 109'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2030' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='12:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED','12:30:00','13:30:00','ACW 109'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2030' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='12:30:00');

-- EECS 2311
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON','14:30:00','15:30:00','ACW 109'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2311' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='14:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED','14:30:00','15:30:00','ACW 109'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2311' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='14:30:00');

-- ========== FALL inserts (TTh courses) ==========
-- EECS 3311
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'TUE','09:30:00','10:30:00','CLH 201'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 3311' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='TUE' AND m.start_time='09:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'THU','09:30:00','10:30:00','CLH 201'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 3311' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='THU' AND m.start_time='09:30:00');

-- EECS 3482
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'TUE','10:30:00','11:30:00','CLH 201'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 3482' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='TUE' AND m.start_time='10:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'THU','10:30:00','11:30:00','CLH 201'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 3482' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='THU' AND m.start_time='10:30:00');

-- EECS 4481
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'TUE','12:30:00','13:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 4481' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='TUE' AND m.start_time='12:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'THU','12:30:00','13:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 4481' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='THU' AND m.start_time='12:30:00');

-- EECS 4482
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'TUE','14:30:00','15:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 4482' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='TUE' AND m.start_time='14:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'THU','14:30:00','15:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 4482' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='THU' AND m.start_time='14:30:00');

-- ========== FALL inserts (single-day courses for KINE to avoid conflicts) ==========
-- KINE 1000 (FRI)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'FRI','09:30:00','10:30:00','HNE 100'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 1000' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='FRI' AND m.start_time='09:30:00');

-- KINE 1020 (FRI)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'FRI','10:30:00','11:30:00','HNE 100'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 1020' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='FRI' AND m.start_time='10:30:00');

-- KINE 2011 (FRI)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'FRI','12:30:00','13:30:00','HNE 200'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 2011' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='FRI' AND m.start_time='12:30:00');

-- KINE 2031 (FRI)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'FRI','14:30:00','15:30:00','HNE 200'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 2031' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='FRI' AND m.start_time='14:30:00');

-- KINE 3012 (MON late)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON','16:00:00','17:00:00','HNE 300'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 3012' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='16:00:00');

-- KINE 3030 (WED late)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED','16:00:00','17:00:00','HNE 300'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 3030' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='16:00:00');

-- KINE 4010 (TUE late)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'TUE','16:00:00','17:00:00','HNE 400'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 4010' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='TUE' AND m.start_time='16:00:00');

-- KINE 4020 (THU late)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'THU','16:00:00','17:00:00','HNE 400'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 4020' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='THU' AND m.start_time='16:00:00');

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

-- EECS 1011 (WINTER TTh 09:30)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'TUE','09:30:00','10:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 1011' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='TUE' AND m.start_time='09:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'THU','09:30:00','10:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 1011' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='THU' AND m.start_time='09:30:00');

-- MATH 1013 (WINTER TTh 10:30)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'TUE','10:30:00','11:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='MATH 1013' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='TUE' AND m.start_time='10:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'THU','10:30:00','11:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='MATH 1013' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='THU' AND m.start_time='10:30:00');

-- EECS 2030 (WINTER TTh 12:30)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'TUE','12:30:00','13:30:00','ACW 109'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2030' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='TUE' AND m.start_time='12:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'THU','12:30:00','13:30:00','ACW 109'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2030' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='THU' AND m.start_time='12:30:00');

-- EECS 2311 (WINTER TTh 14:30)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'TUE','14:30:00','15:30:00','ACW 109'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2311' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='TUE' AND m.start_time='14:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'THU','14:30:00','15:30:00','ACW 109'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2311' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='THU' AND m.start_time='14:30:00');

-- EECS 3311 (WINTER MW 09:30)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON','09:30:00','10:30:00','CLH 201'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 3311' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='09:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED','09:30:00','10:30:00','CLH 201'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 3311' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='09:30:00');

-- EECS 3482 (WINTER MW 10:30)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON','10:30:00','11:30:00','CLH 201'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 3482' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='10:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED','10:30:00','11:30:00','CLH 201'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 3482' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='10:30:00');

-- EECS 4481 (WINTER MW 12:30)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON','12:30:00','13:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 4481' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='12:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED','12:30:00','13:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 4481' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='12:30:00');

-- EECS 4482 (WINTER MW 14:30)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON','14:30:00','15:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 4482' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='14:30:00');
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED','14:30:00','15:30:00','LAS 1004'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 4482' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='14:30:00');

-- WINTER: KINE courses (same single-day pattern as FALL so no conflicts)
-- KINE 1000 (FRI)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'FRI','09:30:00','10:30:00','HNE 100'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 1000' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='FRI' AND m.start_time='09:30:00');

INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'FRI','10:30:00','11:30:00','HNE 100'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 1020' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='FRI' AND m.start_time='10:30:00');

INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'FRI','12:30:00','13:30:00','HNE 200'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 2011' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='FRI' AND m.start_time='12:30:00');

INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'FRI','14:30:00','15:30:00','HNE 200'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 2031' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='FRI' AND m.start_time='14:30:00');

INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON','16:00:00','17:00:00','HNE 300'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 3012' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='16:00:00');

INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED','16:00:00','17:00:00','HNE 300'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 3030' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='16:00:00');

INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'TUE','16:00:00','17:00:00','HNE 400'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 4010' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='TUE' AND m.start_time='16:00:00');

INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'THU','16:00:00','17:00:00','HNE 400'
FROM sections s JOIN courses c ON c.id=s.course_id JOIN terms t ON t.id=s.term_id
WHERE c.course_code='KINE 4020' AND t.season='WINTER' AND t.year=2027 AND s.section_code='A'
AND NOT EXISTS (SELECT 1 FROM section_meetings m WHERE m.section_id=s.id AND m.day_of_week='THU' AND m.start_time='16:00:00');