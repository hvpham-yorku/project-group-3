# =========================
# FILE: database/README-TA.md
# =========================
# Database Setup (TA) — Docker (Recommended)
#
# This project supports two modes:
# 1) SQL mode (real MySQL database + Flyway migrations)
# 2) STUB mode (no database; JSON stub data)
#
# This README is the TA-friendly setup using Docker (fast + consistent).

## 0) Requirements
# - Docker Desktop installed and running
# Download Docker Desktop:
# https://www.docker.com/products/docker-desktop/

## 1) Start MySQL with Docker
# From the repo root:

docker compose -f database/docker-compose.yml up -d

## 2) Verify the container is running
docker ps

## 3) Database connection details (Docker)
# Host: localhost
# Port: 3306
# Database: yupathbuilder
# Username: yupath
# Password: yupathpass
# Root password: root
#
# Note: Flyway runs automatically when the backend starts and will create/seed tables.

## 4) Run backend (SQL mode)
# Windows (PowerShell):
cd backend
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run

# macOS/Linux:
# cd backend
# ./mvnw spring-boot:run

## 5) Run backend (STUB mode — no DB required)
# Windows (PowerShell):
cd backend
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=stub

# macOS/Linux:
# cd backend
# ./mvnw spring-boot:run -Dspring-boot.run.profiles=stub

## 6) Stop the database
# From the repo root:
docker compose -f database/docker-compose.yml down

## 7) Reset the database completely (delete all data)
docker compose -f database/docker-compose.yml down -v
docker compose -f database/docker-compose.yml up -d

## Demo Login (for quick testing)

Use this account to log in and test features immediately:

Email: jostin@test.com
Password: 123456

If the account does not exist yet:
1) Open the Register page
2) Register with a email/password above
3) Select a Faculty (engineering) and Program (Software engineering)
4) Login normally

Note: In STUB mode, the program/course data comes from JSON stub files.
In SQL mode, the program/course data comes from the MySQL database and Flyway migrations.

## What to test inside the app (ITR2)

1) Auth
- Register a new user (first/last/email/password + faculty/program selection)
- Login using email + password

2) Course Search
- Search courses (e.g., "EECS", "MATH")
- Open “More Info” and verify sections/meetings display for the selected term

3) Build Schedule
- Add courses to Selected Courses
- Click Build Schedule
- Verify the generated schedule matches the term and shows day/time/location

4) Program Checklist
- Verify checklist loads and shows courses grouped by year (STUB: minimal; SQL: seeded)