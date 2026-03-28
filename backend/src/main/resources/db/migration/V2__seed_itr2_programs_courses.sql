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

-- Kinesiology —  
INSERT INTO courses(course_code, subject, catalog_number, title, description)
VALUES
-- First Year / Basic Sciences
('BIOL 1000', 'BIOL', '1000', 'Biology I', 'Introductory biology (Year 1).'),
('BIOL 1001', 'BIOL', '1001', 'Biology II', 'Continuation of biology (Year 1).'),
('CHEM 1000', 'CHEM', '1000', 'Chemical Structure', 'Introductory chemistry concepts (Year 1).'),
('CHEM 1001', 'CHEM', '1001', 'Chemical Dynamic', 'Chemical reactions and dynamics (Year 1).'),

-- Physics (split OR options)
('PHYS 1410', 'PHYS', '1410', 'Physics with Applications to Life Sciences', 'Physics for life sciences (Year 1).'),
('PHYS 1420', 'PHYS', '1420', 'Physics with Applications to Physical Science', 'Physics for physical sciences (Year 1).'),
('PHYS 1411', 'PHYS', '1411', 'Physics Fundamentals 1', 'Intro physics fundamentals (Year 1).'),
('PHYS 1421', 'PHYS', '1421', 'Physics with Life Science Applications 1', 'Physics for life sciences I (Year 1).'),
('PHYS 1412', 'PHYS', '1412', 'Physics Fundamentals 2', 'Continuation of physics fundamentals (Year 1).'),
('PHYS 1422', 'PHYS', '1422', 'Physics with Life Science Applications 2', 'Physics for life sciences II (Year 1).'),

-- General Education
('HUMA 1105', 'HUMA', '1105', 'Myth and Imagination in Greece and Rome', 'Humanities elective (General Education).'),
('SOSC 1009', 'SOSC', '1009', 'Introduction to Social Science (ESL)', 'Intro social science (General Education).'),

-- Math Options
('MATH 1506', 'MATH', '1506', 'Math I for Biological and Health Sciences', 'Biological math I.'),
('MATH 1507', 'MATH', '1507', 'Math II for Biological and Health Sciences', 'Biological math II.'),
('MATH 1505', 'MATH', '1505', 'Mathematics for Life and Social Sciences', 'Applied math for life sciences.'),
('MATH 1014', 'MATH', '1014', 'Calculus in Polar Coordinates', 'Calculus concepts.'),
('MATH 1025', 'MATH', '1025', 'Applied Linear Algebra', 'Linear algebra fundamentals.'),

-- Computing Options
('EECS 1520', 'EECS', '1520', 'Computer Use: Fundamentals', 'Basic computing skills.'),
('EECS 1540', 'EECS', '1540', 'Computer Use for the Natural Sciences', 'Computing for science.'),
('EECS 1570', 'EECS', '1570', 'Computing for Psychology', 'Computing in psychology.'),

-- Core KINE Courses
('KINE 1000', 'KINE', '1000', 'Kinesiology: Sociocultural Perspectives', 'Foundations of kinesiology (Year 1).'),
('KINE 1020', 'KINE', '1020', 'Fitness and Health', 'Fitness and health basics (Year 1).'),
('KINE 2011', 'KINE', '2011', 'Human Physiology I', 'Intro human physiology (Year 2).'),
('KINE 2031', 'KINE', '2031', 'Human Anatomy', 'Intro human anatomy (Year 2).'),
('KINE 2049', 'KINE', '2049', 'Research Methods in Kinesiology', 'Research fundamentals.'),
('KINE 2050', 'KINE', '2050', 'Analysis of Data in Kinesiology I', 'Data analysis basics.'),
('KINE 3000', 'KINE', '3000', 'Psychology of Physical Activity & Health', 'Psychology in activity.'),
('KINE 3012', 'KINE', '3012', 'Human Physiology II', 'Advanced physiology (Year 3).'),
('KINE 3020', 'KINE', '3020', 'Skilled Performance & Motor Learning', 'Motor learning concepts.'),
('KINE 3030', 'KINE', '3030', 'Biomechanics of Human Movement', 'Biomechanics fundamentals (Year 3).'),
('KINE 4010', 'KINE', '4010', 'Exercise Physiology', 'Exercise physiology (Year 4).'),
('KINE 4020', 'KINE', '4020', 'Human Nutrition', 'Human nutrition (Year 4).'),

-- 4000 Level Options
('KINE 4375', 'KINE', '4375', 'Body as Weapon', 'Advanced kinesiology topic.'),
('KINE 4340', 'KINE', '4340', 'Sport and "Race" in Canada', 'Sociology of sport.'),

-- Additional KINE
('KINE 3440', 'KINE', '3440', 'Olympic Games: Heroes and Villains', 'Olympic studies.'),
('KINE 3620', 'KINE', '3620', 'Sociology of Sport I', 'Sport sociology.'),
('KINE 3400', 'KINE', '3400', 'Fitness Consulting and Personal Fitness', 'Fitness consulting.'),
('KINE 3100', 'KINE', '3100', 'Health Psychology and Kinesiology', 'Health psychology.'),

-- Outside Major Science
('PSYC 1010', 'PSYC', '1010', 'Introduction to Psychology', 'Intro psychology.'),
('BIOL 2040', 'BIOL', '2040', 'Genetics', 'Genetics fundamentals.'),
('MATH 1510', 'MATH', '1510', 'Fundamentals of Mathematics', 'Math fundamentals.'),

-- Practicum (0 credit)
('PKIN 0570', 'PKIN', '0570', 'Ballroom Dance I', 'Practicum course.'),
('PKIN 0295', 'PKIN', '0295', 'Pre-Swim I', 'Practicum course.'),
('PKIN 0303', 'PKIN', '0303', 'Women''s Basketball I', 'Practicum course.'),
('PKIN 0440', 'PKIN', '0440', 'Badminton I', 'Practicum course.'),
('PKIN 0811', 'PKIN', '0811', 'Clinical Placement in Athletic Therapy I', 'Practicum course.'),
('PKIN 0812', 'PKIN', '0812', 'Clinical Placement in Athletic Therapy II', 'Practicum course.'),
('PKIN 0840', 'PKIN', '0840', 'Elementary and Recreational Games', 'Practicum course.'),
('PKIN 0861', 'PKIN', '0861', 'Personal Training I', 'Practicum course.'),
('PKIN 0862', 'PKIN', '0862', 'Personal Training II', 'Practicum course.'),

-- Aquatics
('PKIN 0200', 'PKIN', '0200', 'Swimming I', 'Aquatics practicum.'),
('PKIN 0240', 'PKIN', '0240', 'Lifesaving', 'Aquatics practicum.'),
('PKIN 0270', 'PKIN', '0270', 'Skin Diving', 'Aquatics practicum.'),

-- Emergency Care
('PKIN 0751', 'PKIN', '0751', 'Advanced First Aid/CPR', 'Emergency care practicum.'),
('PKIN 0750', 'PKIN', '0750', 'Emergency Care I', 'Emergency care practicum.'),
('PKIN 0770', 'PKIN', '0770', 'First Aid/CPR Instructor', 'Emergency care practicum.'),

-- Track & Field
('PKIN 0600', 'PKIN', '0600', 'Track and Field I', 'Track practicum.'),
('PKIN 0610', 'PKIN', '0610', 'Track and Field II', 'Track practicum.')
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

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'REQUIRED', 'Core', 3
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 2049'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=2 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'REQUIRED', 'Core', 4
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 2050'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=2 AND r.course_id=c.id
);

-- Year 3
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'REQUIRED', 'Core', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 3000'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=3 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'REQUIRED', 'Core', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 3012'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=3 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'REQUIRED', 'Core', 3
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 3020'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.year_level=3 AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'REQUIRED', 'Core', 4
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
--Elective Groups
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 4, c.id, 'ELECTIVE', 'KINE 4000 Level Options', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 4375'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 4, c.id, 'ELECTIVE', 'KINE 4000 Level Options', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 4340'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

-- Physics options (choose one path)
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Physics Options', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='PHYS 1410'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Physics Options', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='PHYS 1420'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

-- Physics sequence
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Physics Sequence', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='PHYS 1411'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Physics Sequence', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='PHYS 1412'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Physics Sequence', 3
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='PHYS 1421'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Physics Sequence', 4
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='PHYS 1422'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

-- Math options (6 credits required)
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Math Options', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='MATH 1506'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Math Options', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='MATH 1507'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Math Options', 3
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='MATH 1505'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Math Options', 4
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='MATH 1014'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Math Options', 5
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='MATH 1025'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);
--GENERAL EDUCATION
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'General Education', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='HUMA 1105'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'General Education', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='SOSC 1009'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);
--EECS (COMPUTING OPTIONS)
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Computing Options', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='EECS 1520'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Computing Options', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='EECS 1540'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Computing Options', 3
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='EECS 1570'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);
--ADDITIONAL KINE ELECTIVES
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'ELECTIVE', 'KINE Electives', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 3440'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'ELECTIVE', 'KINE Electives', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 3620'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'ELECTIVE', 'KINE Electives', 3
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 3400'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 3, c.id, 'ELECTIVE', 'KINE Electives', 4
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='KINE 3100'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'ELECTIVE', 'Outside Major Science', 1
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='PSYC 1010'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);
--OUTSIDE MAJOR SCIENCE
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'ELECTIVE', 'Outside Major Science', 2
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='BIOL 2040'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 2, c.id, 'ELECTIVE', 'Outside Major Science', 3
FROM programs p, courses c
WHERE p.name='Kinesiology' AND c.course_code='MATH 1510'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r WHERE r.program_id=p.id AND r.course_id=c.id
);
--PRACTICUM
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Practicum', 1
FROM programs p
JOIN courses c ON c.course_code IN (
  'PKIN 0570','PKIN 0295','PKIN 0303','PKIN 0440',
  'PKIN 0811','PKIN 0812','PKIN 0840','PKIN 0861','PKIN 0862',
  'PKIN 0200','PKIN 0240','PKIN 0270',
  'PKIN 0751','PKIN 0750','PKIN 0770',
  'PKIN 0600','PKIN 0610'
)
WHERE p.name='Kinesiology'
AND NOT EXISTS (
  SELECT 1 FROM program_requirements r
  WHERE r.program_id=p.id AND r.course_id=c.id
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