# Database Setup For Teammates

This document describes the SQL-backed backend setup for team members.

## Intended SQL Runtime

The backend defaults to:

- database: `yupathbuilder`
- username: `yupath`
- password: `yupathpass`
- host: `localhost`
- port: `3306`

These values come from:

- `backend/src/main/resources/application.properties`

## Local MySQL Setup

Create the database and user once:

```sql
CREATE DATABASE IF NOT EXISTS yupathbuilder;
CREATE USER IF NOT EXISTS 'yupath'@'localhost' IDENTIFIED BY 'yupathpass';
GRANT ALL PRIVILEGES ON yupathbuilder.* TO 'yupath'@'localhost';
FLUSH PRIVILEGES;
```

Run the backend:

```bat
cd backend
.\mvnw.cmd spring-boot:run
```

Or on macOS/Linux:

```bash
cd backend
./mvnw spring-boot:run
```

## Docker Compose Option

The repository also contains `database/docker-compose.yml`.

Docker now maps host port `3306` to container port `3306`, which matches the backend datasource configuration in `backend/src/main/resources/application.properties`.

If you want to use Docker instead of a local MySQL installation, you can start it directly with:

```bash
docker compose -f database/docker-compose.yml up -d
```

## Flyway

Flyway runs automatically on backend startup in SQL mode and applies the migrations in:

- `backend/src/main/resources/db/migration/`

## Stub Mode

The intended stub command is:

```bat
cd backend
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=stub
```

However, `backend/src/main/resources/application-stub.properties` still contains unresolved merge markers, so stub mode should be reviewed before using it in teammate setup.

## Testing

Backend tests are split between unit and integration suites:

- `.\mvnw.cmd test` runs unit tests
- `.\mvnw.cmd verify` runs unit plus integration tests

The integration tests require a working SQL database.

## Related Documentation

- [`../README.md`](../README.md)
- [`README-TA.md`](README-TA.md)
- [`../backend/src/test/README_TESTS.txt`](../backend/src/test/README_TESTS.txt)
