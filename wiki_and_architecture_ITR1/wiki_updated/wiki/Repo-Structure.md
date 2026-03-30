# Repository Structure - Final Iteration 3 State

This page describes the structure of the repository after the Iteration 3 refactoring and release updates.

## Top Level

- `frontend/` - React + Vite client
- `backend/` - Spring Boot backend
- `database/` - database setup docs and Docker compose
- `docs/` - historical artifacts and log pointers
- `wiki_and_architecture_ITR1/` - exported wiki and planning/architecture material

## Frontend

Important frontend folders:

- `frontend/src/api/` - backend request wrappers
- `frontend/src/components/` - reusable UI components
- `frontend/src/context/` - shared auth/session state
- `frontend/src/pages/` - top-level screens
- `frontend/src/utils/` - helper logic such as schedule conflict analysis

Main pages in the final release:

- `AuthPage.jsx`
- `Dashboard.jsx`
- `ProfilePage.jsx`

## Backend

The final backend is packaged by feature rather than by a flatter technical-role layout.

### Domain And Feature Packages

- `authentication/`
  - login, registration, JWT handling, profile management

- `course_catalog/`
  - course search, course list, course details, section meetings

- `program_system/`
  - faculties, programs, checklist building

- `scheduler_system/`
  - terms, selected courses, schedule building, SQL/stub schedule stores

- `system_status/`
  - lightweight status endpoints

### Supporting Packages

- `config/`
  - Spring Security, Jackson, Flyway startup behavior

- `global_exception_handler/`
  - controller advice classes for REST error mapping

- `util/`
  - small shared helpers such as term parsing

## Resources

The backend resources folder contains:

- `db/migration/` - Flyway schema and seed scripts
- `stub-data/` - JSON files used by stub mode
- `application.properties` - default SQL-backed backend configuration
- `application-stub.properties` - stub-profile overrides

## Tests

The backend tests are explicitly split by type:

- `backend/src/test/java/com/yupathbuilder/backend/unit/`
- `backend/src/test/java/com/yupathbuilder/backend/integration/`

The integration tests target the SQL-backed store seams, not the entire UI stack.

## Documentation

Release documentation is concentrated in:

- `README.md`
- `wiki_and_architecture_ITR1/wiki_updated/wiki/`
- `wiki_and_architecture_ITR1/wiki_updated/Planning_document_ITR2.md`
- `wiki_and_architecture_ITR1/wiki_updated/Planning_document_ITR3.md`

## Important Final-State Notes

- The repository still contains documentation and artifacts from earlier iterations.
- The final release intentionally preserves that history instead of flattening everything into a single new narrative.
- Some infrastructure docs still include practical repository-state notes so setup and traceability remain accurate.
