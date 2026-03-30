# YU Path Builder

YU Path Builder is a full-stack course planning application for York University students. The system allows an authenticated user to register, log in, browse the course catalog, inspect section details for a specific term, save selected courses per term, build a weekly schedule, and review the checklist for the user's program.

This repository contains the final Iteration 3 state of the project. The frontend is a React + Vite application, and the backend is a Spring Boot REST API with JWT-based authentication, MySQL/Flyway support, and a parallel stub-data mode.

## Final Iteration 3 Scope

The final repository state shows the following user-facing capabilities:

- User registration and login
- Authenticated profile viewing and editing
- Password change for authenticated users
- Faculty and program selection
- Program checklist retrieval for the authenticated user
- Course search
- Course details by term, including section meetings
- Saved selected courses per term
- Schedule generation for multiple terms, including Summer 2027
- Conflict visualization in the generated schedule

The codebase also contains important Iteration 3 engineering work:

- Backend refactoring from a flatter package layout into feature-based modules
- Clearer store seams for SQL-backed and stub-backed behavior
- Explicit unit and integration test separation in the backend test suite

## Iteration 3 Highlights

Compared with the earlier iteration documentation, Iteration 3 introduced or finalized the following major changes:

1. Profile management became part of the authenticated workflow.
   The frontend now includes a profile page, and the backend exposes `/api/authentication/profile` and `/api/authentication/profile/password`.

2. Selected courses are now persisted per term.
   The backend exposes `/api/me/selected-courses`, and the dashboard reloads saved selections after refresh.

3. Schedule support was extended to additional seeded terms.
   The current repository includes Summer 2027 data and tests for that term.

4. The backend was reorganized by feature.
   The final package structure separates `authentication`, `course_catalog`, `program_system`, `scheduler_system`, and `system_status`.

5. Documentation and test boundaries were improved.
   The backend integration tests now document the SQL seams around the store interfaces, and the wiki has been updated to describe the final release state.

## Repository Layout

```text
project-group-3/
|-- frontend/                         React + Vite client
|-- backend/                          Spring Boot backend
|-- database/                         Database setup documentation and Docker compose
|-- docs/                             Historical project artifacts and log pointers
`-- wiki_and_architecture_ITR1/       Wiki export and planning/architecture docs
```

The final backend package layout is:

```text
backend/src/main/java/com/yupathbuilder/backend/
|-- authentication/
|-- config/
|-- course_catalog/
|-- global_exception_handler/
|-- program_system/
|-- scheduler_system/
|-- system_status/
`-- util/
```

## Documentation Index

The repository keeps the final release documentation in the following files:

- Root overview: [`README.md`](README.md)
- Wiki home: [`wiki_and_architecture_ITR1/wiki_updated/wiki/Home.md`](wiki_and_architecture_ITR1/wiki_updated/wiki/Home.md)
- Run guide: [`wiki_and_architecture_ITR1/wiki_updated/wiki/How-to-Run.md`](wiki_and_architecture_ITR1/wiki_updated/wiki/How-to-Run.md)
- Architecture: [`wiki_and_architecture_ITR1/wiki_updated/wiki/Architecture.md`](wiki_and_architecture_ITR1/wiki_updated/wiki/Architecture.md)
- API summary: [`wiki_and_architecture_ITR1/wiki_updated/wiki/API-Endpoints.md`](wiki_and_architecture_ITR1/wiki_updated/wiki/API-Endpoints.md)
- Repository structure: [`wiki_and_architecture_ITR1/wiki_updated/wiki/Repo-Structure.md`](wiki_and_architecture_ITR1/wiki_updated/wiki/Repo-Structure.md)
- Refactoring notes: [`wiki_and_architecture_ITR1/wiki_updated/wiki/Refactoring.md`](wiki_and_architecture_ITR1/wiki_updated/wiki/Refactoring.md)
- Preserved ITR2 plan: [`wiki_and_architecture_ITR1/wiki_updated/Planning_document_ITR2.md`](wiki_and_architecture_ITR1/wiki_updated/Planning_document_ITR2.md)
- ITR3 planning and traceability: [`wiki_and_architecture_ITR1/wiki_updated/Planning_document_ITR3.md`](wiki_and_architecture_ITR1/wiki_updated/Planning_document_ITR3.md)
- Project log: [`wiki_and_architecture_ITR1/wiki_updated/wiki/log.md`](wiki_and_architecture_ITR1/wiki_updated/wiki/log.md)

## Running the Project

The current repository supports two backend modes in principle:

- SQL mode using MySQL + Flyway
- STUB mode using JSON-backed data

Detailed instructions are in the wiki run guide and the database READMEs:

- [`wiki_and_architecture_ITR1/wiki_updated/wiki/How-to-Run.md`](wiki_and_architecture_ITR1/wiki_updated/wiki/How-to-Run.md)
- [`database/README-TA.md`](database/README-TA.md)
- [`database/README-TEAM.md`](database/README-TEAM.md)

Quick summary:

### Frontend

```bash
cd frontend
npm install
npm run dev
```

### Backend, intended SQL mode

```bash
cd backend
./mvnw spring-boot:run
```

On Windows:

```bat
cd backend
.\mvnw.cmd spring-boot:run
```

### Backend, intended stub mode

```bash
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=stub
```

On Windows:

```bat
cd backend
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=stub
```

## Testing

Backend test execution is documented in [`backend/src/test/README_TESTS.txt`](backend/src/test/README_TESTS.txt).

Summary:

- Unit tests: `.\mvnw.cmd test` or `./mvnw test`
- Integration tests: `.\mvnw.cmd verify` or `./mvnw verify`

The integration tests are SQL-backed and exercise the store seams against a real MySQL database.

## Traceability To Earlier Iterations

This repository does not erase the earlier iteration story. The final release still traces back to the initial goals:

- Build a term schedule
- Support longer-term degree planning
- Save planning work
- Provide rule or checklist feedback

By the end of Iteration 3:

- Term-based schedule building is delivered
- Checklist-based requirement feedback is delivered
- Saved course selections by term are delivered
- Full degree timeline planning and plan comparison are still not implemented as complete end-user workflows

See the planning documents for the detailed evolution:

- [`wiki_and_architecture_ITR1/wiki_updated/Planning_document_ITR2.md`](wiki_and_architecture_ITR1/wiki_updated/Planning_document_ITR2.md)
- [`wiki_and_architecture_ITR1/wiki_updated/Planning_document_ITR3.md`](wiki_and_architecture_ITR1/wiki_updated/Planning_document_ITR3.md)

## Team

- Jostin Martinez Castillo
- Fejuku Oyinkansola Barbara
- Wamiq Lakha
- Jaicks Reuben
