# ===============================
# FILE: database/README-TEAM.md
# ===============================
# Database Setup (Team) — Local MySQL Install
#
# This README is for teammates who want a local MySQL installation (no Docker).
# The backend uses Flyway migrations to create and seed tables automatically.

## 0) Download MySQL
# Official MySQL downloads:
# MySQL Community Server:
# https://dev.mysql.com/downloads/mysql/
#
# MySQL Workbench (optional GUI — NOT required):
# https://dev.mysql.com/downloads/workbench/

## 1) Install MySQL Server
# Install MySQL Community Server and ensure the MySQL service is running.

## 2) Create database + user (one time)
# Open MySQL Workbench (or any SQL client) and run:

# SQL:
# CREATE DATABASE IF NOT EXISTS yupathbuilder;
#
# CREATE USER IF NOT EXISTS 'yupath'@'localhost' IDENTIFIED BY 'yupathpass';
# GRANT ALL PRIVILEGES ON yupathbuilder.* TO 'yupath'@'localhost';
# FLUSH PRIVILEGES;

## 3) Configure backend connection
# Edit:
# backend/src/main/resources/application.properties
#
# Make sure it contains (or matches):
# spring.datasource.url=jdbc:mysql://localhost:3306/yupathbuilder?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
# spring.datasource.username=yupath
# spring.datasource.password=yupathpass

## 4) Run backend (SQL mode)
# Windows (PowerShell):
cd backend
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run

# macOS/Linux:
# cd backend
# ./mvnw spring-boot:run
# docker exec -it yupath-mysql mysql -u yupath -p yupathbuilder

## 5) What happens on startup?
# Flyway runs migrations automatically from:
# backend/src/main/resources/db/migration/
#
# It will create tables and seed initial data (no manual import needed).

## 6) Run backend (STUB mode — no DB required)
# If you want to run without MySQL:
cd backend
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=stub

## What to do in case of a Docker error (Posible solution)

That Docker error means port 3306 is already being used on your PC (usually a local MySQL service), so Docker can’t bind it.

1) Check what’s using 3306:
netstat -aon | findstr :3306
Then see the process name:
tasklist /FI "PID eq <PID>"

2) If it’s MySQL (mysqld / MySQL80), stop it:
Open Services (Win+R -> services.msc) -> find “MySQL80” (or “MySQL”) -> Stop
Then run again:
docker compose -f database/docker-compose.yml up -d

Alternative (if you want to keep local MySQL running):
Change docker-compose ports from "3306:3306" to "3307:3306"
And update backend DB URL to use localhost:3307

If docker ps shows nothing, try:
docker ps -a
docker compose -f database/docker-compose.yml ps
