# Database Setup For TAs

This document explains how the SQL-backed version of YU Path Builder is intended to run for evaluation.

The project has two backend modes:

- SQL mode using MySQL + Flyway
- STUB mode using JSON-backed data

For the final repository state, SQL mode is the best-supported evaluation path.

## Option 1: Use Local MySQL On Port 3306

This is the simplest way to match the backend defaults as currently committed.

Expected database settings:

- Host: `localhost`
- Port: `3306`
- Database: `yupathbuilder`
- Username: `yupath`
- Password: `yupathpass`

Then run the backend:

```bat
cd backend
.\mvnw.cmd spring-boot:run
```

Or on macOS/Linux:

```bash
cd backend
./mvnw spring-boot:run
```

## Option 2: Use The Provided Docker Compose

The repository includes:

```bash
docker compose -f database/docker-compose.yml up -d
```

This starts MySQL on host port `3306`, which now matches the backend SQL datasource configuration in `backend/src/main/resources/application.properties`.

That means the Dockerized database can be used directly with the committed backend SQL configuration.

## Flyway Behavior

When SQL mode starts successfully, Flyway runs automatically from:

- `backend/src/main/resources/db/migration/`

The SQL migrations create and seed the database content used by:

- catalog search
- course details
- terms
- schedule building
- user accounts
- saved selected courses
- program requirements

## Frontend

Start the frontend in a separate terminal:

```bat
cd frontend
npm install
npm run dev
```

## Stub Mode Note

The intended stub command is:

```bat
cd backend
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=stub
```

However, `backend/src/main/resources/application-stub.properties` still contains unresolved merge markers, so stub mode should be reviewed before using it for evaluation.

## Suggested TA Validation Paths

If the backend is running successfully, the most important flows to validate are:

1. Register and log in
2. Search courses by code or keyword
3. Inspect course details for a selected term
4. Save and remove selected courses by term
5. Build a schedule
6. Review the program checklist
7. Open the profile page and test profile updates

## Related Documentation

- [`../README.md`](../README.md)
- [`README-TEAM.md`](README-TEAM.md)
- [`../wiki_and_architecture_ITR1/wiki_updated/wiki/How-to-Run.md`](../wiki_and_architecture_ITR1/wiki_updated/wiki/How-to-Run.md)
