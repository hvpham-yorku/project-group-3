# Repo Structure (ITR2)

## Top-level
- `frontend/` — React + Vite UI
- `backend/` — Spring Boot API
- `database/` — DB setup for ITR2
  - `docker-compose.yml`
  - `README-TA.md` (Docker instructions)
  - `README-TEAM.md` (Local MySQL instructions)

## Frontend (`frontend/`)
- `src/` — React components
  - `components/auth/` — Login/Register cards
  - `components/dashboard/` — Course search, checklist, selected courses
  - `pages/` — AuthPage, Dashboard
  - `api/` — API wrappers
- `vite.config.js` — proxy config (`/api -> http://localhost:8080`)

## Backend (`backend/`)
- `src/main/java/com/yupathbuilder/backend/`
  - `controller/` — REST controllers
  - `service/` — business logic (SQL mode)
  - `repo/` — JPA repositories (SQL mode)
  - `store/` — **ITR2 DI layer** (interfaces)
    - SQL implementations (MySQL)
    - STUB implementations (JSON)
  - `store/stub/` — stub helper rules (time slots, etc.)

- `src/main/resources/`
  - `db/migration/` — Flyway migrations + seed data
  - `stub-data/` — JSON stub data (runs without DB)
  - `application.properties` — SQL mode defaults
  - `application-stub.properties` — STUB mode overrides

## ITR2 checklist mapping
- **Real DB + migrations**: `backend/src/main/resources/db/migration/`
- **STUB vs SQL switching**: `app.store=sql|stub` + Spring DI
- **DB setup folder**: `database/`
- **Project log**: `wiki/log.md`
