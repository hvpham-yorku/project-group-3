-- Add missing PKIN meeting times and intentionally create some overlap blocks
-- so timetable conflict detection has realistic cases.

-- FALL 2026 PKIN meetings
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id,
       CASE c.course_code
         WHEN 'PKIN 0570' THEN 'MON'
         WHEN 'PKIN 0295' THEN 'MON'
         WHEN 'PKIN 0303' THEN 'TUE'
         WHEN 'PKIN 0440' THEN 'TUE'
         WHEN 'PKIN 0811' THEN 'WED'
         WHEN 'PKIN 0812' THEN 'WED'
         WHEN 'PKIN 0840' THEN 'THU'
         WHEN 'PKIN 0861' THEN 'THU'
         WHEN 'PKIN 0862' THEN 'THU'
         WHEN 'PKIN 0200' THEN 'FRI'
         WHEN 'PKIN 0240' THEN 'FRI'
         WHEN 'PKIN 0270' THEN 'FRI'
         WHEN 'PKIN 0751' THEN 'MON'
         WHEN 'PKIN 0750' THEN 'MON'
         WHEN 'PKIN 0770' THEN 'MON'
         WHEN 'PKIN 0600' THEN 'WED'
         WHEN 'PKIN 0610' THEN 'WED'
       END AS day_of_week,
       CASE c.course_code
         WHEN 'PKIN 0570' THEN '10:00:00'
         WHEN 'PKIN 0295' THEN '10:00:00'
         WHEN 'PKIN 0303' THEN '10:00:00'
         WHEN 'PKIN 0440' THEN '10:00:00'
         WHEN 'PKIN 0811' THEN '13:00:00'
         WHEN 'PKIN 0812' THEN '13:00:00'
         WHEN 'PKIN 0840' THEN '15:00:00'
         WHEN 'PKIN 0861' THEN '15:00:00'
         WHEN 'PKIN 0862' THEN '16:00:00'
         WHEN 'PKIN 0200' THEN '09:00:00'
         WHEN 'PKIN 0240' THEN '09:00:00'
         WHEN 'PKIN 0270' THEN '10:00:00'
         WHEN 'PKIN 0751' THEN '17:00:00'
         WHEN 'PKIN 0750' THEN '17:00:00'
         WHEN 'PKIN 0770' THEN '18:00:00'
         WHEN 'PKIN 0600' THEN '14:00:00'
         WHEN 'PKIN 0610' THEN '14:00:00'
       END AS start_time,
       CASE c.course_code
         WHEN 'PKIN 0570' THEN '11:00:00'
         WHEN 'PKIN 0295' THEN '11:00:00'
         WHEN 'PKIN 0303' THEN '11:00:00'
         WHEN 'PKIN 0440' THEN '11:00:00'
         WHEN 'PKIN 0811' THEN '14:00:00'
         WHEN 'PKIN 0812' THEN '14:00:00'
         WHEN 'PKIN 0840' THEN '16:00:00'
         WHEN 'PKIN 0861' THEN '16:00:00'
         WHEN 'PKIN 0862' THEN '17:00:00'
         WHEN 'PKIN 0200' THEN '10:00:00'
         WHEN 'PKIN 0240' THEN '10:00:00'
         WHEN 'PKIN 0270' THEN '11:00:00'
         WHEN 'PKIN 0751' THEN '18:00:00'
         WHEN 'PKIN 0750' THEN '18:00:00'
         WHEN 'PKIN 0770' THEN '19:00:00'
         WHEN 'PKIN 0600' THEN '15:00:00'
         WHEN 'PKIN 0610' THEN '15:00:00'
       END AS end_time,
       CASE c.course_code
         WHEN 'PKIN 0200' THEN 'POOL A'
         WHEN 'PKIN 0240' THEN 'POOL A'
         WHEN 'PKIN 0270' THEN 'POOL B'
         WHEN 'PKIN 0600' THEN 'TRACK 1'
         WHEN 'PKIN 0610' THEN 'TRACK 1'
         ELSE 'TFC GYM'
       END AS location
FROM sections s
JOIN courses c ON c.id = s.course_id
JOIN terms t ON t.id = s.term_id
WHERE t.season = 'FALL'
  AND t.year = 2026
  AND c.course_code IN (
    'PKIN 0570','PKIN 0295','PKIN 0303','PKIN 0440','PKIN 0811','PKIN 0812',
    'PKIN 0840','PKIN 0861','PKIN 0862','PKIN 0200','PKIN 0240','PKIN 0270',
    'PKIN 0751','PKIN 0750','PKIN 0770','PKIN 0600','PKIN 0610'
  )
  AND NOT EXISTS (
    SELECT 1 FROM section_meetings m WHERE m.section_id = s.id
  );

-- WINTER 2027 PKIN meetings (shifted but still with overlaps)
INSERT INTO section_meetings(section_id, day_of_week, start_time, end_time, location)
SELECT s.id,
       CASE c.course_code
         WHEN 'PKIN 0570' THEN 'TUE'
         WHEN 'PKIN 0295' THEN 'TUE'
         WHEN 'PKIN 0303' THEN 'WED'
         WHEN 'PKIN 0440' THEN 'WED'
         WHEN 'PKIN 0811' THEN 'THU'
         WHEN 'PKIN 0812' THEN 'THU'
         WHEN 'PKIN 0840' THEN 'FRI'
         WHEN 'PKIN 0861' THEN 'FRI'
         WHEN 'PKIN 0862' THEN 'FRI'
         WHEN 'PKIN 0200' THEN 'MON'
         WHEN 'PKIN 0240' THEN 'MON'
         WHEN 'PKIN 0270' THEN 'MON'
         WHEN 'PKIN 0751' THEN 'TUE'
         WHEN 'PKIN 0750' THEN 'TUE'
         WHEN 'PKIN 0770' THEN 'TUE'
         WHEN 'PKIN 0600' THEN 'THU'
         WHEN 'PKIN 0610' THEN 'THU'
       END AS day_of_week,
       CASE c.course_code
         WHEN 'PKIN 0570' THEN '11:00:00'
         WHEN 'PKIN 0295' THEN '11:00:00'
         WHEN 'PKIN 0303' THEN '11:00:00'
         WHEN 'PKIN 0440' THEN '11:00:00'
         WHEN 'PKIN 0811' THEN '14:00:00'
         WHEN 'PKIN 0812' THEN '14:00:00'
         WHEN 'PKIN 0840' THEN '16:00:00'
         WHEN 'PKIN 0861' THEN '16:00:00'
         WHEN 'PKIN 0862' THEN '17:00:00'
         WHEN 'PKIN 0200' THEN '10:00:00'
         WHEN 'PKIN 0240' THEN '10:00:00'
         WHEN 'PKIN 0270' THEN '11:00:00'
         WHEN 'PKIN 0751' THEN '18:00:00'
         WHEN 'PKIN 0750' THEN '18:00:00'
         WHEN 'PKIN 0770' THEN '19:00:00'
         WHEN 'PKIN 0600' THEN '15:00:00'
         WHEN 'PKIN 0610' THEN '15:00:00'
       END AS start_time,
       CASE c.course_code
         WHEN 'PKIN 0570' THEN '12:00:00'
         WHEN 'PKIN 0295' THEN '12:00:00'
         WHEN 'PKIN 0303' THEN '12:00:00'
         WHEN 'PKIN 0440' THEN '12:00:00'
         WHEN 'PKIN 0811' THEN '15:00:00'
         WHEN 'PKIN 0812' THEN '15:00:00'
         WHEN 'PKIN 0840' THEN '17:00:00'
         WHEN 'PKIN 0861' THEN '17:00:00'
         WHEN 'PKIN 0862' THEN '18:00:00'
         WHEN 'PKIN 0200' THEN '11:00:00'
         WHEN 'PKIN 0240' THEN '11:00:00'
         WHEN 'PKIN 0270' THEN '12:00:00'
         WHEN 'PKIN 0751' THEN '19:00:00'
         WHEN 'PKIN 0750' THEN '19:00:00'
         WHEN 'PKIN 0770' THEN '20:00:00'
         WHEN 'PKIN 0600' THEN '16:00:00'
         WHEN 'PKIN 0610' THEN '16:00:00'
       END AS end_time,
       CASE c.course_code
         WHEN 'PKIN 0200' THEN 'POOL A'
         WHEN 'PKIN 0240' THEN 'POOL A'
         WHEN 'PKIN 0270' THEN 'POOL B'
         WHEN 'PKIN 0600' THEN 'TRACK 1'
         WHEN 'PKIN 0610' THEN 'TRACK 1'
         ELSE 'TFC GYM'
       END AS location
FROM sections s
JOIN courses c ON c.id = s.course_id
JOIN terms t ON t.id = s.term_id
WHERE t.season = 'WINTER'
  AND t.year = 2027
  AND c.course_code IN (
    'PKIN 0570','PKIN 0295','PKIN 0303','PKIN 0440','PKIN 0811','PKIN 0812',
    'PKIN 0840','PKIN 0861','PKIN 0862','PKIN 0200','PKIN 0240','PKIN 0270',
    'PKIN 0751','PKIN 0750','PKIN 0770','PKIN 0600','PKIN 0610'
  )
  AND NOT EXISTS (
    SELECT 1 FROM section_meetings m WHERE m.section_id = s.id
  );
