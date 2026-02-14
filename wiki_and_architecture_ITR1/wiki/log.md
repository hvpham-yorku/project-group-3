# log.md — ITR1

This log documents progress, meeting minutes, design decisions, work allocation, and risks for ITR1.

---

## Meeting minutes

### 2026-02-03 (Kickoff + scope)
- **Attendees:** Jostin Martinez Castillo, Fejuku Oyinkansola Barbara, Wamiq Lakha, Jaicks Reuben, Taha Usama
- **Agenda:**
  - Confirm ITR1 requirements (GUI + backend + tests + wiki/log)
  - Define the “big story” for ITR1
  - Choose tech stack and repo structure
- **Decisions:**
  - Big story for ITR1: **Build a conflict-free weekly schedule** from selected courses (term-based).
  - Stack: **React + Vite** frontend; **Spring Boot** backend; stub data from resource files (CSV).
  - Split repo into `frontend/` and `backend/` folders.
- **Action items:**
  - **Jostin:** set up initial repo structure + basic pages (due 2026-02-05)
  - **Wamiq:** draft API routes + auth approach (due 2026-02-05)
  - **Taha:** propose schedule algorithm approach + conflict rules (due 2026-02-06)
  - **Fejuku:** start UI sketch for schedule grid (due 2026-02-06)
  - **Jaicks/Reuben:** create Jira tasks and assign owners (due 2026-02-04)

---

### 2026-02-06 (Auth + API alignment)
- **Attendees:** Jostin Martinez Castillo, Wamiq Lakha, Taha Usama
- **Agenda:**
  - Decide how frontend authenticates with backend
  - Confirm endpoints needed for ITR1
- **Decisions:**
  - Use token-based auth (simple bearer token stored in local storage).
  - Required endpoints for ITR1:
    - `POST /api/auth/login`
    - `POST /api/auth/register`
    - `GET /api/search/courses?q=`
    - `GET /api/courses` (protected)
    - `POST /api/schedule/build` (protected)
- **Action items:**
  - **Wamiq:** implement auth endpoints + token response (due 2026-02-08)
  - **Jostin:** wire frontend login form to `/api/auth/login` (due 2026-02-08)
  - **Taha:** define error status behavior (409 vs 400) for schedule build (due 2026-02-09)

---

### 2026-02-08 (Schedule building logic)
- **Attendees:** Jostin Martinez Castillo, Taha Usama, Fejuku Oyinkansola Barbara, Jaicks Reuben
- **Agenda:**
  - Implement first version of schedule builder
  - Decide on conflict detection
- **Decisions:**
  - Use **backtracking**: try one section per course, reject conflicts, continue until solution found.
  - Conflict rule: overlapping time on same day is not allowed.
  - Days encoding supports York style (ex: **R = Thursday**).
- **Action items:**
  - **Taha:** implement conflict check helper + unit tests outline (due 2026-02-10)
  - **Jostin:** implement schedule build endpoint + integrate service (due 2026-02-10)
  - **Fejuku:** verify UI schedule layout for multiple courses (due 2026-02-11)

---

### 2026-02-10 (Frontend integration + debugging)
- **Attendees:** Jostin Martinez Castillo, Wamiq Lakha, Jaicks Reuben
- **Agenda:**
  - Connect UI to schedule build API
  - Debug common integration issues (proxy errors, term mismatch)
- **Decisions:**
  - Frontend uses Vite proxy to forward `/api/*` to backend.
  - Add clear error messaging when backend is unreachable (ECONNREFUSED).
  - Term value in UI must match backend data (course sections filtered by term).
- **Action items:**
  - **Jostin:** confirm Vite proxy + backend run instructions (due 2026-02-11)
  - **Jaicks/Reuben:** validate sections dataset format and term values (due 2026-02-11)

---

### 2026-02-12 (UI polish + deliverables)
- **Attendees:** Jostin Martinez Castillo, Fejuku Oyinkansola Barbara, Taha Usama, Wamiq Lakha
- **Agenda:**
  - Polish schedule view (wider layout, better readability)
  - Improve login/register UX
  - Ensure wiki + architecture sketch + log are ready for ITR1
- **Decisions:**
  - When schedule exists, expand layout width for better schedule readability.
  - Replace raw JSON debug output with user-friendly UI (remove big black `<pre>`).
  - Login/register should look professional; register UI collects first/last name (backend support later).
- **Action items:**
  - **Jostin:** finalize UI polish + remove debug UI (due 2026-02-13)
  - **Fejuku:** review UI spacing/alignment on different screen sizes (due 2026-02-13)
  - **Wamiq:** finalize backend packaging instructions and verify endpoints (due 2026-02-13)
  - **Taha:** add/verify unit tests for conflict detection and schedule building (due 2026-02-13)

---

## Design decisions (rationale)

### 1) React + Vite for frontend
- **Alternatives considered:** plain HTML/JS; CRA
- **Why we chose it:** fast dev server, simple proxying to backend, clean component structure.
- **Impact:** easy to iterate on UI quickly during ITR1.

### 2) Spring Boot for backend
- **Alternatives considered:** Node/Express
- **Why we chose it:** strong structure for controllers/services/repositories and easy testing with JUnit.
- **Impact:** clear separation of concerns and easier grading/maintenance.

### 3) Stub data in resource files (CSV) for ITR1
- **Alternatives considered:** real DB (Postgres/MySQL)
- **Why we chose it:** fewer moving parts; deterministic local runs; matches ITR1 requirement for a stub DB.
- **Impact:** schedule building depends on correct term/course fields in CSV.

### 4) Backtracking schedule algorithm
- **Alternatives considered:** greedy pick-first; brute-force without pruning
- **Why we chose it:** backtracking naturally handles “choose 1 section per course” and prunes conflicts early.
- **Impact:** works well for small-to-medium sets of selected courses; needs tests for edge cases.

### 5) UI expands horizontally after “Build Schedule”
- **Alternatives considered:** keep fixed max-width; force full-width always
- **Why we chose it:** keeps the UI compact during browsing, but improves readability when schedule is displayed.
- **Impact:** better user experience on wide screens.

---

## Work log (who did what)

| Date | Member | Task | Est. (h) | Actual (h) | Notes |
|---|---|---:|---:|---:|---|
| 2026-02-03 | Jaicks Reuben | Create Jira board + initial stories/tasks | 1.0 | 1.0 | ITR1 tasks created and assigned |
| 2026-02-04 | Jostin Martinez Castillo | Repo scaffold (frontend/backend folders) | 2.0 | 2.5 | Vite + Spring Boot setup |
| 2026-02-06 | Wamiq Lakha | Auth endpoints design + response format | 2.0 | 2.0 | Token-based auth chosen |
| 2026-02-08 | Taha Usama | Conflict detection rules + helper design | 2.0 | 2.5 | Day parsing includes R=Thu |
| 2026-02-10 | Jostin Martinez Castillo | Frontend integration for schedule build | 3.0 | 3.5 | Fixed proxy/connection issues |
| 2026-02-12 | Fejuku Oyinkansola Barbara | UI review + schedule layout feedback | 1.5 | 1.5 | Requested wider schedule + cleaner layout |
| 2026-02-12 | Jostin Martinez Castillo | Login/register + schedule UI polish | 3.0 | 3.0 | Removed debug raw JSON, improved layout |
| 2026-02-13 | Wamiq Lakha | Verify endpoints + run instructions | 1.0 | 1.0 | Confirmed backend run steps |
| 2026-02-13 | Taha Usama | Unit tests for schedule/conflicts | 3.0 | 2.5 | Add more edge cases if time |

---

## Concerns / risks

- **Term mismatch:** if UI term does not match CSV term values, schedule build returns no sections.
- **Data format:** CSV parsing depends on consistent column order and time format (`HH:mm` / `HH:mm:ss`).
- **Schedule edge cases:** overlapping boundaries (e.g., one ends exactly when another starts), multi-day sections, missing days.
- **Auth handling:** token storage and protected routes; needs consistent error messages for expired/invalid token.
- **Testing coverage:** risk of few tests for overlap logic and schedule search branching.
- **Usability:** schedule readability on smaller screens (needs horizontal scroll or responsive layout).
