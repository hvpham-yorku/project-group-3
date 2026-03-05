-- V2__seed_minimal.sql
-- Seed mínimo para probar: roles, faculty/program, terms, courses, sections, meetings

-- 1) Roles
INSERT INTO roles(name) VALUES ('USER')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO roles(name) VALUES ('ADMIN')
ON DUPLICATE KEY UPDATE name = name;

-- 2) Faculty + Program
INSERT INTO faculties(name) VALUES ('Engineering')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO programs(faculty_id, name, degree)
SELECT f.id, 'Software Engineering', 'BEng'
FROM faculties f
WHERE f.name = 'Engineering'
AND NOT EXISTS (
  SELECT 1 FROM programs p WHERE p.name = 'Software Engineering'
);

-- 3) Terms
INSERT INTO terms(season, year) VALUES ('FALL', 2026)
ON DUPLICATE KEY UPDATE year = year;

INSERT INTO terms(season, year) VALUES ('WINTER', 2027)
ON DUPLICATE KEY UPDATE year = year;

-- 4) Courses (algunos ejemplos para buscar por "software", "security", etc.)
INSERT INTO courses(course_code, subject, catalog_number, title, description)
VALUES
('EECS 2311', 'EECS', '2311', 'Software Development Project', 'Project-based software engineering course'),
('EECS 2030', 'EECS', '2030', 'Advanced Object Oriented Programming', 'OOP concepts and Java development'),
('EECS 3214', 'EECS', '3214', 'Computer Networks', 'Networking fundamentals, protocols, and routing'),
('EECS 3221', 'EECS', '3221', 'Operating System Fundamentals', 'Processes, threads, memory, file systems'),
('EECS 4413', 'EECS', '4413', 'Building E-Commerce Systems', 'Web development and e-commerce systems'),
('EECS 4443', 'EECS', '4443', 'Mobile User Interface Design', 'Designing and evaluating mobile UI/UX'),
('EECS 4421', 'EECS', '4421', 'Introduction to Data Mining', 'Data mining and machine learning basics'),
('EECS 4236', 'EECS', '4236', 'Software Security', 'Secure software principles and common vulnerabilities')
ON DUPLICATE KEY UPDATE
title = VALUES(title),
description = VALUES(description);

-- 5) Program requirements (checklist) para Software Engineering (Year 1/2 ejemplo)
-- Nota: esto es demo. Luego lo reemplazas por la lista real.
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Software Engineering' AND c.course_code='EECS 2030'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r
  WHERE r.program_id=p.id AND r.year_level=2 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Software Engineering' AND c.course_code='EECS 2311'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r
  WHERE r.program_id=p.id AND r.year_level=2 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Software Engineering' AND c.course_code='EECS 3221'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r
  WHERE r.program_id=p.id AND r.year_level=3 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 4, c.id, 'ELECTIVE', 'Electives', 1
FROM programs p, courses c
WHERE p.name='Software Engineering' AND c.course_code='EECS 4236'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r
  WHERE r.program_id=p.id AND r.year_level=4 AND r.course_id=c.id
);

-- 6) Sections + Meetings
-- Helper: ids de terms
-- FALL 2026 term_id
-- WINTER 2027 term_id

-- EECS 2311 (FALL 2026) Section A
INSERT INTO sections(course_id, term_id, section_code, component, instructor, capacity, enrolled)
SELECT c.id, t.id, 'A', 'LEC', 'TBA', 100, 0
FROM courses c JOIN terms t
WHERE c.course_code='EECS 2311' AND t.season='FALL' AND t.year=2026
AND NOT EXISTS (
  SELECT 1 FROM sections s
  WHERE s.course_id=c.id AND s.term_id=t.id AND s.section_code='A' AND s.component='LEC'
);

-- Meetings for EECS 2311 A (Mon/Wed 10:30-11:30)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON', '10:30:00', '11:30:00', 'Room TBD'
FROM sections s
JOIN courses c ON c.id=s.course_id
JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2311' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (
  SELECT 1 FROM section_meetings m
  WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='10:30:00'
);

INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED', '10:30:00', '11:30:00', 'Room TBD'
FROM sections s
JOIN courses c ON c.id=s.course_id
JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2311' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (
  SELECT 1 FROM section_meetings m
  WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='10:30:00'
);

-- EECS 2311 (WINTER 2027) Section B
INSERT INTO sections(course_id, term_id, section_code, component, instructor, capacity, enrolled)
SELECT c.id, t.id, 'B', 'LEC', 'TBA', 100, 0
FROM courses c JOIN terms t
WHERE c.course_code='EECS 2311' AND t.season='WINTER' AND t.year=2027
AND NOT EXISTS (
  SELECT 1 FROM sections s
  WHERE s.course_id=c.id AND s.term_id=t.id AND s.section_code='B' AND s.component='LEC'
);

-- Meetings for EECS 2311 B (Tue/Thu 14:30-15:30)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'TUE', '14:30:00', '15:30:00', 'Room TBD'
FROM sections s
JOIN courses c ON c.id=s.course_id
JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2311' AND t.season='WINTER' AND t.year=2027 AND s.section_code='B'
AND NOT EXISTS (
  SELECT 1 FROM section_meetings m
  WHERE m.section_id=s.id AND m.day_of_week='TUE' AND m.start_time='14:30:00'
);

INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'THU', '14:30:00', '15:30:00', 'Room TBD'
FROM sections s
JOIN courses c ON c.id=s.course_id
JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2311' AND t.season='WINTER' AND t.year=2027 AND s.section_code='B'
AND NOT EXISTS (
  SELECT 1 FROM section_meetings m
  WHERE m.section_id=s.id AND m.day_of_week='THU' AND m.start_time='14:30:00'
);

-- EECS 2030 (FALL 2026) Section A
INSERT INTO sections(course_id, term_id, section_code, component, instructor, capacity, enrolled)
SELECT c.id, t.id, 'A', 'LEC', 'TBA', 150, 0
FROM courses c JOIN terms t
WHERE c.course_code='EECS 2030' AND t.season='FALL' AND t.year=2026
AND NOT EXISTS (
  SELECT 1 FROM sections s
  WHERE s.course_id=c.id AND s.term_id=t.id AND s.section_code='A' AND s.component='LEC'
);

-- Meetings for EECS 2030 A (Mon/Wed 16:00-17:30)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'MON', '16:00:00', '17:30:00', 'Room TBD'
FROM sections s
JOIN courses c ON c.id=s.course_id
JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2030' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (
  SELECT 1 FROM section_meetings m
  WHERE m.section_id=s.id AND m.day_of_week='MON' AND m.start_time='16:00:00'
);

INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id, 'WED', '16:00:00', '17:30:00', 'Room TBD'
FROM sections s
JOIN courses c ON c.id=s.course_id
JOIN terms t ON t.id=s.term_id
WHERE c.course_code='EECS 2030' AND t.season='FALL' AND t.year=2026 AND s.section_code='A'
AND NOT EXISTS (
  SELECT 1 FROM section_meetings m
  WHERE m.section_id=s.id AND m.day_of_week='WED' AND m.start_time='16:00:00'
);

