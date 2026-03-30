# How To Run - Final Iteration 3 State

This guide explains the project as it exists in the repository at the end of Iteration 3.

## Prerequisites

- Node.js
- Java 25
- Maven Wrapper support through `mvnw` or `mvnw.cmd`
- MySQL if running SQL mode directly
- Docker Desktop or Docker Engine if using the provided MySQL container

## Frontend

Run the frontend from the repository root:

```bat
cd frontend
npm install
npm run dev
```

The Vite dev server typically starts on:

- `http://localhost:5173/`

## Backend Modes

The backend is designed to support:

- SQL mode using MySQL + Flyway
- STUB mode using JSON-backed stub data

## SQL Mode: Current Default Configuration

The committed default backend datasource points to:

- host: `localhost`
- port: `3306`
- database: `yupathbuilder`
- username: `yupath`
- password: `yupathpass`

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

## SQL Mode Using The Repository Docker Compose

The repository includes:

```bash
docker compose -f database/docker-compose.yml up -d
```

The Docker setup now matches the committed SQL backend configuration:

- `database/docker-compose.yml` exposes MySQL on host port `3306`
- `backend/src/main/resources/application.properties` expects MySQL on host port `3306`

That means the backend can connect to the Dockerized database without changing `application.properties`.

Recommended flow:

1. Start MySQL with Docker:

```bash
docker compose -f database/docker-compose.yml up -d
```

2. Start the backend:

```bat
cd backend
.\mvnw.cmd spring-boot:run
```

Or on macOS/Linux:

```bash
cd backend
./mvnw spring-boot:run
```

If port `3306` is already in use on your machine, stop the conflicting local MySQL service first, or change both `database/docker-compose.yml` and `backend/src/main/resources/application.properties` together so they stay aligned.

## STUB Mode

The intended stub-mode command is:

```bat
cd backend
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=stub
```

Or on macOS/Linux:

```bash
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=stub
```

Repository note:

- `backend/src/main/resources/application-stub.properties` still contains unresolved merge markers, so stub mode should be reviewed before using it for local runs

## Backend Test Commands

Backend test layout is documented in `backend/src/test/README_TESTS.txt`.

Commands:

```bat
cd backend
.\mvnw.cmd test
.\mvnw.cmd verify
```

Notes:

- `test` runs unit tests
- `verify` runs the SQL-backed integration tests as well
- the integration tests require a working MySQL database

## Suggested End-To-End Run Sequence

1. Start or prepare a MySQL instance that matches the backend datasource configuration.
2. Start the backend.
3. Start the frontend in a separate terminal.
4. Open the frontend in the browser.
5. Register a user and test the authenticated flows.

## Features Worth Testing In ITR3

1. Registration and login
2. Course search
3. Course details by term
4. Save selected courses per term
5. Build a schedule
6. Review the checklist
7. Open the profile page and update profile details
8. Change the account password

## Related Documentation

- [`Home.md`](Home.md)
- [`API-Endpoints.md`](API-Endpoints.md)
- [`Architecture.md`](Architecture.md)
- [`../Planning_document_ITR3.md`](../Planning_document_ITR3.md)
