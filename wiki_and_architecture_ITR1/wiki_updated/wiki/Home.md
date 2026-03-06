# YU Path Builder — ITR2 Wiki

Welcome! This wiki explains how to run the project locally for **Iteration 2 (ITR2)**, the overall architecture, and the key endpoints/files.

## Quick links
- **How to Run (SQL + STUB)**: `How-to-Run.md`
- **Architecture (DI: SQL vs STUB)**: `Architecture.md`
- **API Endpoints**: `API-Endpoints.md`
- **Repo Structure** (includes `/database`): `Repo-Structure.md`
- **Project Log**: `log.md`

## What changed for ITR2

### Persistence
The backend supports **two persistence modes** (switchable via configuration):
- **SQL mode**: MySQL + Flyway migrations/seed data
- **STUB mode**: JSON stub data (runs without MySQL)

### Key features demonstrated in both modes
- Course search
- Course details (“More Info”)
- Build schedule
- Program checklist

### Database setup
We include a top-level **`database/`** folder (same level as `backend/` and `frontend/`) with setup instructions:
- `database/README-TA.md` (Docker)
- `database/README-TEAM.md` (Local MySQL)

