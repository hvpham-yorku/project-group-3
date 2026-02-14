# Repo Structure

> Adjust names if your repo differs.

## Top-level
- `frontend/` — React + Vite UI
- `backend/` — Spring Boot API

## Frontend
- `src/` — React components + CSS
- `vite.config.js` — proxy config (`/api -> http://localhost:<port>`)

## Backend
- `src/main/java/...` — controllers, services, repositories, models
- `src/main/resources/data/` — stub data (CSV/JSON)

## ITR1 deliverables checklist
- Source code organized by layers/packages
- Tests in `test/` (or `src/test/java` for Java projects)
- Wiki pages (this wiki)
- Architecture sketch (image)
- `log.md` with meeting minutes + design decisions + concerns
