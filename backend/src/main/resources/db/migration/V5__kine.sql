-- ---------------------------------------------------------
-- 1) Faculties
-- ---------------------------------------------------------
INSERT INTO faculties(name) VALUES ('Health')
ON DUPLICATE KEY UPDATE name = name;

-- Kinesiology
INSERT INTO programs(faculty_id, name, degree)
SELECT f.id, 'Kinesiology', 'BSc'
FROM faculties f
WHERE f.name = 'Health'
AND NOT EXISTS (
  SELECT 1 FROM programs p WHERE p.name = 'Kinesiology'
);
 

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
-- Elective Groups
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
-- GENERAL EDUCATION
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
-- EECS (COMPUTING OPTIONS)
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
-- ADDITIONAL KINE ELECTIVES
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
-- OUTSIDE MAJOR SCIENCE
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
-- PRACTICUM
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