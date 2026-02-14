# Architecture

## High-level overview
The system is a simple client/server web app:

- **Frontend (React + Vite)**: UI for login/register, searching courses, selecting courses, and building a schedule.
- **Backend (Spring Boot)**: REST API for auth + course search + schedule generation.
- **Data**: stub data loaded from resource files (CSV/JSON/etc.) so the app runs locally without an external DB.

## Components
- **React UI**
  - Auth pages (login/register)
  - Dashboard (course search, selected courses)
  - Schedule view (weekly grid/table)
- **Spring Boot API**
  - Auth controller (login/register -> token)
  - Courses controller (list + search)
  - Schedule controller (build schedule)
- **Repositories (stub)**
  - Read data from resources (CSV) into in-memory lists

## Data flow (build schedule)
1. User selects course codes in the UI
2. UI calls `POST /api/schedule/build` with `{ term, courseCodes }`
3. Backend loads candidate sections for each course/term
4. ScheduleService runs a search/backtracking algorithm to pick non-conflicting sections
5. Backend returns the chosen sections; UI renders them in the weekly grid

## Sketch
An exported image is included in the repo/wiki:
- `assets/architecture_sketch.png`

You can also keep this Mermaid diagram (optional):

```mermaid
flowchart LR
  UI[React + Vite Frontend] -->|/api/*| API[Spring Boot Backend]
  API --> REPO[Repositories (in-memory)]
  REPO --> DATA[(Resource Files: courses/sections CSV)]
  API --> SCHED[ScheduleService / Backtracking]
  SCHED --> API
  API --> UI
```
