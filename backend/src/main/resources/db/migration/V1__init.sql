-- V1__init.sql

-- 1) Security / users
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  program_id BIGINT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- 2) Programs / checklist
CREATE TABLE faculties (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(120) NOT NULL UNIQUE
);

CREATE TABLE programs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  faculty_id BIGINT NULL,
  name VARCHAR(160) NOT NULL,
  degree VARCHAR(60) NULL,
  CONSTRAINT fk_programs_faculty FOREIGN KEY (faculty_id) REFERENCES faculties(id)
);

ALTER TABLE users
  ADD CONSTRAINT fk_users_program FOREIGN KEY (program_id) REFERENCES programs(id);

-- 3) Courses / terms / sections
CREATE TABLE courses (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  course_code VARCHAR(30) NOT NULL UNIQUE,     -- ejemplo: "EECS 2311"
  subject VARCHAR(10) NULL,                    -- ejemplo: "EECS"
  catalog_number VARCHAR(10) NULL,             -- ejemplo: "2311"
  title VARCHAR(255) NOT NULL,
  description TEXT NULL
);

-- Text search (optional; useful for "software")
-- Note: FULLTEXT works well in InnoDB on modern MySQL.
CREATE FULLTEXT INDEX ft_courses_title_desc ON courses (title, description);

CREATE TABLE terms (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  season ENUM('FALL','WINTER','SUMMER') NOT NULL,
  year INT NOT NULL,
  UNIQUE (season, year)
);

CREATE TABLE sections (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  course_id BIGINT NOT NULL,
  term_id BIGINT NOT NULL,
  section_code VARCHAR(20) NOT NULL,           -- A, B, 01, etc.
  component ENUM('LEC','LAB','TUT','SEM') NOT NULL DEFAULT 'LEC',
  instructor VARCHAR(255) NULL,
  capacity INT NULL,
  enrolled INT NULL,
  CONSTRAINT fk_sections_course FOREIGN KEY (course_id) REFERENCES courses(id),
  CONSTRAINT fk_sections_term FOREIGN KEY (term_id) REFERENCES terms(id),
  INDEX idx_sections_course_term (course_id, term_id)
);

CREATE TABLE section_meetings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  section_id BIGINT NOT NULL,
  day_of_week ENUM('MON','TUE','WED','THU','FRI','SAT','SUN') NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  location VARCHAR(120) NULL,
  CONSTRAINT fk_meetings_section FOREIGN KEY (section_id) REFERENCES sections(id) ON DELETE CASCADE,
  INDEX idx_meetings_section_day_time (section_id, day_of_week, start_time)
);

-- 4) Checklist per program/year
CREATE TABLE program_requirements (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  program_id BIGINT NOT NULL,
  year_level TINYINT NOT NULL,                 -- 1..4
  course_id BIGINT NOT NULL,
  req_type ENUM('REQUIRED','ELECTIVE') NOT NULL DEFAULT 'REQUIRED',
  group_name VARCHAR(120) NULL,
  display_order INT NOT NULL DEFAULT 0,
  CONSTRAINT fk_req_program FOREIGN KEY (program_id) REFERENCES programs(id) ON DELETE CASCADE,
  CONSTRAINT fk_req_course FOREIGN KEY (course_id) REFERENCES courses(id),
  UNIQUE (program_id, year_level, course_id),
  INDEX idx_req_program_year (program_id, year_level)
);

-- 5) User progress on checklist
CREATE TABLE user_course_progress (
  user_id BIGINT NOT NULL,
  course_id BIGINT NOT NULL,
  status ENUM('NOT_STARTED','IN_PROGRESS','COMPLETED') NOT NULL DEFAULT 'NOT_STARTED',
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, course_id),
  CONSTRAINT fk_ucp_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_ucp_course FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- 6) Save user schedules
CREATE TABLE user_schedules (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  term_id BIGINT NOT NULL,
  name VARCHAR(100) DEFAULT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_user_schedules_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_user_schedules_term FOREIGN KEY (term_id) REFERENCES terms(id),
  INDEX idx_user_schedules_user_term (user_id, term_id)
);

CREATE TABLE user_schedule_sections (
  schedule_id BIGINT NOT NULL,
  section_id BIGINT NOT NULL,
  PRIMARY KEY (schedule_id, section_id),
  CONSTRAINT fk_uss_schedule FOREIGN KEY (schedule_id) REFERENCES user_schedules(id) ON DELETE CASCADE,
  CONSTRAINT fk_uss_section FOREIGN KEY (section_id) REFERENCES sections(id)
);