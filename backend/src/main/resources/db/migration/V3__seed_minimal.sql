-- V2__seed_minimal.sql
-- Seed MINIMO (sin cursos / sin programs / sin sections / sin meetings)
-- La data real para ITR2 (programs, courses, requirements, sections, meetings) está en V3.

-- 1) Roles (si tu schema tiene tabla roles)
INSERT INTO roles(name) VALUES ('USER')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO roles(name) VALUES ('ADMIN')
ON DUPLICATE KEY UPDATE name = name;

-- (Opcional) Si quieres garantizar que existan los terms ANTES de V3, puedes dejar esto.
-- No es necesario porque V3 ya los crea, pero no hace daño.
INSERT INTO terms(season, year) VALUES ('FALL', 2026)
ON DUPLICATE KEY UPDATE year = year;

INSERT INTO terms(season, year) VALUES ('WINTER', 2027)
ON DUPLICATE KEY UPDATE year = year;