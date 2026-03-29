CREATE TABLE user_selected_courses (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  term_id BIGINT NOT NULL,
  course_id BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user_selected_courses_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_user_selected_courses_term FOREIGN KEY (term_id) REFERENCES terms(id),
  CONSTRAINT fk_user_selected_courses_course FOREIGN KEY (course_id) REFERENCES courses(id),
  CONSTRAINT uq_user_selected_courses_user_course UNIQUE (user_id, course_id),
  CONSTRAINT uq_user_selected_courses_user_term_course UNIQUE (user_id, term_id, course_id),
  INDEX idx_user_selected_courses_user_term (user_id, term_id)
);
