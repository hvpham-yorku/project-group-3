-- Align Kinesiology checklist groups and required courses with
-- "fundamental courses for kinesology - Sheet1.csv"

-- 1) Add missing Introductory Science requirements (present in CSV)
INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Introductory Science Courses', 1
FROM programs p, courses c
WHERE p.name = 'Kinesiology' AND c.course_code = 'BIOL 1000'
  AND NOT EXISTS (
    SELECT 1 FROM program_requirements r
    WHERE r.program_id = p.id AND r.year_level = 1 AND r.course_id = c.id
  );

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Introductory Science Courses', 2
FROM programs p, courses c
WHERE p.name = 'Kinesiology' AND c.course_code = 'BIOL 1001'
  AND NOT EXISTS (
    SELECT 1 FROM program_requirements r
    WHERE r.program_id = p.id AND r.year_level = 1 AND r.course_id = c.id
  );

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Introductory Science Courses', 3
FROM programs p, courses c
WHERE p.name = 'Kinesiology' AND c.course_code = 'CHEM 1000'
  AND NOT EXISTS (
    SELECT 1 FROM program_requirements r
    WHERE r.program_id = p.id AND r.year_level = 1 AND r.course_id = c.id
  );

INSERT INTO program_requirements(program_id, year_level, course_id, req_type, group_name, display_order)
SELECT p.id, 1, c.id, 'ELECTIVE', 'Introductory Science Courses', 4
FROM programs p, courses c
WHERE p.name = 'Kinesiology' AND c.course_code = 'CHEM 1001'
  AND NOT EXISTS (
    SELECT 1 FROM program_requirements r
    WHERE r.program_id = p.id AND r.year_level = 1 AND r.course_id = c.id
  );

-- 2) Rename physics group labels to match CSV wording
UPDATE program_requirements r
JOIN programs p ON p.id = r.program_id
JOIN courses c ON c.id = r.course_id
SET r.group_name = 'Physics (1410 or 1420)',
    r.display_order = CASE c.course_code WHEN 'PHYS 1410' THEN 1 ELSE 2 END
WHERE p.name = 'Kinesiology' AND c.course_code IN ('PHYS 1410', 'PHYS 1420');

UPDATE program_requirements r
JOIN programs p ON p.id = r.program_id
JOIN courses c ON c.id = r.course_id
SET r.group_name = 'Physics (1411 or 1421)',
    r.display_order = CASE c.course_code WHEN 'PHYS 1411' THEN 1 ELSE 2 END
WHERE p.name = 'Kinesiology' AND c.course_code IN ('PHYS 1411', 'PHYS 1421');

UPDATE program_requirements r
JOIN programs p ON p.id = r.program_id
JOIN courses c ON c.id = r.course_id
SET r.group_name = 'Physics (1412 or 1422)',
    r.display_order = CASE c.course_code WHEN 'PHYS 1412' THEN 1 ELSE 2 END
WHERE p.name = 'Kinesiology' AND c.course_code IN ('PHYS 1412', 'PHYS 1422');

-- 3) Make outside-major groups match CSV split
UPDATE program_requirements r
JOIN programs p ON p.id = r.program_id
JOIN courses c ON c.id = r.course_id
SET r.group_name = 'Outside Major Science (9 credits)',
    r.display_order = CASE c.course_code
      WHEN 'PSYC 1010' THEN 1
      WHEN 'BIOL 2040' THEN 2
      ELSE r.display_order
    END
WHERE p.name = 'Kinesiology' AND c.course_code IN ('PSYC 1010', 'BIOL 2040');

UPDATE program_requirements r
JOIN programs p ON p.id = r.program_id
JOIN courses c ON c.id = r.course_id
SET r.group_name = 'Additional Outside Major Science (6 credits)',
    r.display_order = 1
WHERE p.name = 'Kinesiology' AND c.course_code = 'MATH 1510';

-- 4) Split practicum requirements by category to mirror CSV
UPDATE program_requirements r
JOIN programs p ON p.id = r.program_id
JOIN courses c ON c.id = r.course_id
SET r.group_name = 'Practicum (Complete 8)',
    r.display_order = CASE c.course_code
      WHEN 'PKIN 0570' THEN 1
      WHEN 'PKIN 0295' THEN 2
      WHEN 'PKIN 0303' THEN 3
      WHEN 'PKIN 0440' THEN 4
      WHEN 'PKIN 0811' THEN 5
      WHEN 'PKIN 0812' THEN 6
      WHEN 'PKIN 0840' THEN 7
      WHEN 'PKIN 0861' THEN 8
      WHEN 'PKIN 0862' THEN 9
      ELSE r.display_order
    END
WHERE p.name = 'Kinesiology'
  AND c.course_code IN (
    'PKIN 0570', 'PKIN 0295', 'PKIN 0303', 'PKIN 0440',
    'PKIN 0811', 'PKIN 0812', 'PKIN 0840', 'PKIN 0861', 'PKIN 0862'
  );

UPDATE program_requirements r
JOIN programs p ON p.id = r.program_id
JOIN courses c ON c.id = r.course_id
SET r.group_name = 'Practicum - Aquatics (Choose 1)',
    r.display_order = CASE c.course_code
      WHEN 'PKIN 0200' THEN 1
      WHEN 'PKIN 0240' THEN 2
      WHEN 'PKIN 0270' THEN 3
      ELSE r.display_order
    END
WHERE p.name = 'Kinesiology' AND c.course_code IN ('PKIN 0200', 'PKIN 0240', 'PKIN 0270');

UPDATE program_requirements r
JOIN programs p ON p.id = r.program_id
JOIN courses c ON c.id = r.course_id
SET r.group_name = 'Practicum - Emergency Care (Choose 1)',
    r.display_order = CASE c.course_code
      WHEN 'PKIN 0751' THEN 1
      WHEN 'PKIN 0750' THEN 2
      WHEN 'PKIN 0770' THEN 3
      ELSE r.display_order
    END
WHERE p.name = 'Kinesiology' AND c.course_code IN ('PKIN 0751', 'PKIN 0750', 'PKIN 0770');

UPDATE program_requirements r
JOIN programs p ON p.id = r.program_id
JOIN courses c ON c.id = r.course_id
SET r.group_name = 'Practicum - Track and Field (Choose 1)',
    r.display_order = CASE c.course_code
      WHEN 'PKIN 0600' THEN 1
      WHEN 'PKIN 0610' THEN 2
      ELSE r.display_order
    END
WHERE p.name = 'Kinesiology' AND c.course_code IN ('PKIN 0600', 'PKIN 0610');
