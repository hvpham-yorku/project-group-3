# Project Log - Iterations 1 Through 3

This log preserves the progression of the project across the three iterations. Earlier entries retain the original documented meeting-style detail. Iteration 3 has less explicit meeting documentation in the repository, so that section uses conservative repository evidence and clearly marks missing historical detail.

## Scope Of This Log

This log is intended to capture:

- meeting minutes where documented
- major decisions and why they were made
- task assignments and work allocation
- development work by story or task
- estimated and actual effort where confirmed
- project concerns and unresolved issues grounded in the repository

## Iteration 1 And Iteration 2 Historical Entries

The sections below preserve the earlier iteration story because the final release should show how the project evolved rather than replacing that history.

---

## Meeting Minutes

### 2026-02-03 (Kickoff and scope)

- Attendees: Jostin Martinez Castillo, Fejuku Oyinkansola Barbara, Wamiq Lakha, Jaicks Reuben, Taha Usama
- Agenda:
  - confirm ITR1 requirements
  - define the main story for ITR1
  - choose the stack and basic repository structure
- Decisions:
  - main story for ITR1: build a conflict-free weekly schedule from selected courses
  - stack: React + Vite frontend, Spring Boot backend, stub resource data
  - split the repository into `frontend/` and `backend/`
- Action items:
  - Jostin: initial repo structure and basic pages
  - Wamiq: draft API routes and auth approach
  - Taha: propose schedule algorithm approach and conflict rules
  - Fejuku: start UI sketch for the schedule grid
  - Jaicks/Reuben: create Jira tasks and assign owners

### 2026-02-06 (Auth and API alignment)

- Attendees: Jostin Martinez Castillo, Wamiq Lakha, Taha Usama
- Agenda:
  - decide how the frontend authenticates with the backend
  - confirm the endpoints required for ITR1
- Decisions:
  - use token-based auth with bearer token storage
  - required endpoints include login, register, course search, protected course access, and schedule build
- Action items:
  - Wamiq: implement auth endpoints and token response
  - Jostin: wire frontend login to the backend
  - Taha: define error status behavior for schedule build

### 2026-02-08 (Schedule building logic)

- Attendees: Jostin Martinez Castillo, Taha Usama, Fejuku Oyinkansola Barbara, Jaicks Reuben
- Agenda:
  - implement the first version of the schedule builder
  - decide conflict-detection behavior
- Decisions:
  - use backtracking to choose one section per course while pruning conflicts
  - overlapping time on the same day is not allowed
  - support York day encoding such as `R = Thursday`
- Action items:
  - Taha: implement conflict helper and test outline
  - Jostin: implement schedule build endpoint
  - Fejuku: verify UI layout for multiple courses

### 2026-02-10 (Frontend integration and debugging)

- Attendees: Jostin Martinez Castillo, Wamiq Lakha, Jaicks Reuben
- Agenda:
  - connect the UI to the schedule API
  - debug proxy and term-mismatch issues
- Decisions:
  - use the Vite proxy for `/api/*`
  - improve user messaging when the backend is unreachable
  - ensure UI term values match backend data
- Action items:
  - Jostin: verify proxy and backend run instructions
  - Jaicks/Reuben: validate section dataset format and term values

### 2026-02-12 (UI polish and deliverables)

- Attendees: Jostin Martinez Castillo, Fejuku Oyinkansola Barbara, Taha Usama, Wamiq Lakha
- Agenda:
  - polish the schedule view
  - improve login and register UX
  - finalize wiki, architecture sketch, and log for ITR1
- Decisions:
  - widen the layout once a schedule is shown
  - replace raw debug output with user-facing UI
  - collect first and last name in registration
- Action items:
  - Jostin: finalize UI polish
  - Fejuku: review layout responsiveness
  - Wamiq: verify endpoints and backend packaging instructions
  - Taha: add or verify schedule and conflict unit tests

---

## Iteration 2 Meetings

### 2026-03-03 (ITR2 kickoff: DB plan and DI requirement)

- Attendees: Jostin Martinez Castillo, Fejuku Oyinkansola Barbara, Wamiq Lakha, Jaicks Reuben
- Duration: 60 minutes
- Agenda:
  - review ITR2 requirements for a real DB plus DI-based stub switching
  - decide the MySQL + Flyway approach
- Decisions:
  - use MySQL + Flyway for SQL mode
  - keep a stub mode so the features can still run without MySQL
  - keep stub data minimal but feature-complete
- Action items:
  - Jostin: integrate MySQL + Flyway
  - Jaicks/Reuben: confirm ITR2 folder and DI requirements
  - Wamiq: review which endpoints must work in both modes

### 2026-03-04 (ITR2 implementation: backend and frontend integration)

- Attendees: Jostin Martinez Castillo, Fejuku Oyinkansola Barbara, Jaicks Reuben
- Duration: 75 minutes
- Agenda:
  - implement DB schema, seed data, and validation fixes
  - implement SQL vs stub stores for catalog and courses
  - update frontend register/login/checklist/search flows
- Key progress:
  - fixed Hibernate schema validation mismatch
  - added store interfaces and SQL/stub implementations
  - moved stub data to JSON
  - updated register and login flows
- Issue discovered:
  - course details did not refresh correctly when the term changed
- Fix applied:
  - cached details were cleared when the term changed
- Action items:
  - Jostin: make schedule build and course details work in both modes
  - Barbara/Reuben: review UI flow for term switching

### 2026-03-05 (ITR2 hardening: schedule stub, docs, wiki)

- Attendees: Jostin Martinez Castillo, Fejuku Oyinkansola Barbara, Jaicks Reuben
- Duration: 60 minutes
- Agenda:
  - make schedule build and course details consistent in stub mode
  - add TA and team database instructions
  - update the wiki for ITR2
- Key progress:
  - implemented `ScheduleStore` in both SQL and stub modes
  - aligned stub time rules with displayed details
  - added the top-level `database/` folder with setup instructions
  - updated the wiki for SQL vs stub modes
- Action items:
  - Jostin: finalize README and log updates
  - Team: continue unit and integration tests

---

## Iteration 3 Timeline From Repository Evidence

No detailed ITR3 meeting minutes were found in the committed repository documentation. The following entries are therefore based on verified Git history and the final codebase.

### 2026-03-22 (Profile workflow expansion)

- Repository evidence:
  - commit `2c3798e Add profile management and polished account navigation`
- Confirmed changes:
  - profile page added to the frontend
  - authenticated profile endpoints exist in the backend
  - profile update and password change workflows are part of the released application
- Rationale supported by code:
  - Iteration 3 expanded beyond basic authentication into account management
- Meeting detail status:
  - No dedicated meeting record for this work item was found in the committed repository artifacts.
  - The repository supports the feature outcome, but not a confirmed attendee list, duration, or hour-based estimate.

### 2026-03-25 (Term persistence and expanded schedule coverage)

- Repository evidence:
  - commit `c0f66b1 Persist saved course selections by term`
  - commit `36d5c9b Seed sections for all courses in summer 2027 and improve schedule errors`
  - commit `eb30702 changing the .sql files version numbers, adding the logic for new summer terms, saving courses within terms, and switching between terms`
- Confirmed changes:
  - saved course selections are persisted per user and term
  - Summer 2027 became a supported seeded term
  - schedule-building logic was extended to additional term scenarios
- Rationale supported by code:
  - these changes move the product closer to persistent multi-term planning, even though a full degree timeline UI was not delivered
- Meeting detail status:
  - No committed meeting-minute record was found for this implementation step.
  - Repository evidence supports the scope of the work, but not a precise hour-based estimate.

### 2026-03-29 (Feature-based backend refactor and release hardening)

- Repository evidence:
  - commit `9f4be53 refactored backend... packaged by feature`
  - merge `594b983 Merging 'RJ_RE-Organize' into 'main'`
  - commit `7123d5a docs: document backend and frontend modules for ITR3`
- Confirmed changes:
  - backend packages were reorganized by feature
  - release documentation was brought in line with the final repository state
- Rationale supported by code and tests:
  - clearer feature boundaries make controller/service/store/repository responsibilities easier to follow
  - the integration tests map more naturally onto the feature-scoped store seams
- Meeting detail status:
  - No dedicated refactor meeting note was found in the committed documentation.
  - The repository confirms the engineering outcome, but not the specific discussion participants or hour-based task estimates.

---

## Design Decisions And Rationale

### 1. React + Vite For The Frontend

- Alternatives noted earlier: plain HTML/JS, Create React App
- Rationale: fast local iteration, simple API integration, component-based UI
- Final impact: the frontend now supports an authenticated multi-page workflow with shared auth state

### 2. Spring Boot For The Backend

- Alternatives noted earlier: Node/Express
- Rationale: structured controller/service/repository layering and good testing support
- Final impact: the backend could be extended into feature-based modules without changing the overall framework

### 3. SQL And Stub Modes

- Rationale: maintain a real database path for persistence and testing while keeping a stub-backed path for lighter-weight demos
- Final impact: clear store seams exist, but the final committed stub-profile configuration still has unresolved merge markers

### 4. Feature-Based Backend Packaging In ITR3

- Earlier state: flatter package organization by technical role
- Rationale: improve maintainability, navigation, and ownership by domain area
- Final impact: final package structure is much clearer, especially for authentication, program, catalog, and scheduler flows

### 5. Per-Term Saved Course Selections

- Rationale: a student's selected courses should belong to a specific term rather than to a single global unsaved list
- Final impact: the dashboard reloads saved selections and the backend persists them as user data

### 6. Checklist-Based Requirement Feedback

- Earlier planning theme: eligibility and rule feedback
- Final interpretation: checklist-based program requirements rather than a full advising engine
- Final impact: the project delivers requirement visibility, but not automatic course recommendations

---

## Work Log

The earlier iteration entries below preserve the documented estimates and actuals that were already recorded.

| Date | Member | Task | Est. (h) | Actual (h) | Notes |
|---|---|---|---:|---:|---|
| 2026-02-03 | Jaicks Reuben | Create Jira board and initial stories/tasks | 1.0 | 1.0 | ITR1 tasks created and assigned |
| 2026-02-04 | Jostin Martinez Castillo | Repo scaffold (frontend/backend folders) | 2.0 | 2.5 | Vite + Spring Boot setup |
| 2026-02-06 | Wamiq Lakha | Auth endpoints design and response format | 2.0 | 2.0 | Token-based auth chosen |
| 2026-02-08 | Taha Usama | Conflict detection rules and helper design | 2.0 | 2.5 | Includes York day parsing |
| 2026-02-10 | Jostin Martinez Castillo | Frontend integration for schedule build | 3.0 | 3.5 | Fixed proxy issues |
| 2026-02-12 | Fejuku Oyinkansola Barbara | UI review and schedule layout feedback | 1.5 | 1.5 | Wider schedule requested |
| 2026-02-12 | Jostin Martinez Castillo | Login/register and schedule UI polish | 3.0 | 3.0 | Removed debug raw JSON |
| 2026-02-13 | Wamiq Lakha | Verify endpoints and run instructions | 1.0 | 1.0 | Backend run steps checked |
| 2026-02-13 | Taha Usama | Unit tests for schedule/conflicts | 3.0 | 2.5 | More edge cases left for later |
| 2026-03-03 | Jostin Martinez Castillo | MySQL + Flyway integration plan | 2.0 | 2.0 | Schema and seed approach |
| 2026-03-04 | Jostin Martinez Castillo | Implement DB, migrations, and schema fixes | 6.0 | 12.0 | Took longer than expected |
| 2026-03-04 | Jostin Martinez Castillo | Implement DI stores and stub JSON data | 4.0 | 5.0 | CatalogStore and CourseStore |
| 2026-03-04 | Fejuku Oyinkansola Barbara | UI updates and register/login improvements | 3.0 | 3.0 | Faculty/program selection |
| 2026-03-05 | Jostin Martinez Castillo | Schedule and course-details stub consistency | 3.0 | 3.0 | Details aligned with schedule output |
| 2026-03-05 | Jaicks Reuben | Jira updates and story tracking | 1.5 | 1.5 | ITR2 tasks updated |
| 2026-03-05 | Jostin Martinez Castillo | Database folder and TA/team setup docs | 2.0 | 2.0 | Docker and local-setup docs |
| 2026-03-05 | Jostin Martinez Castillo | Wiki updates for ITR2 | 2.0 | 2.0 | SQL vs stub documentation |

The traceability tables below complement the work log by tying the delivered milestones to repository-visible commits and tags. Because they are Git-grounded rather than meeting-grounded, they should be read as release traceability rather than as a complete list of every small contribution.

## Iteration 1 Task Traceability

The table below records ITR1 work that can be confirmed directly from repository tags and commits.

| Date | Evidence | Primary contributor visible in Git history | Planned task allocation | Est. (h) | Actual (h) |
|---|---|---|---|---:|---:|
| 2026-02-08 | `v0.0.1` / `Initial project structure and docs` | ReubenJ | Best interpreted as repository bootstrap and documentation baseline for the first working increment | Medium | Medium |
| 2026-02-08 | `jostin-v0.0.1` / `Add course API + in-memory repo + update frontend UI` | Jostin | Best interpreted as early backend/frontend integration for course browsing and the initial user-facing workflow | Medium | Medium |
| 2026-02-13 | `Organized files in frontend` and `Added Architecture Sketch` | ReubenJ | Best interpreted as UI structure cleanup and release-documentation support for the ITR1 package | Medium | Medium |
| 2026-02-13 | `v0.0.2` / `ITR1: backend auth/scheduling, frontend UI updates, and wiki docs` | Jostin | Best interpreted as the consolidated ITR1 feature release covering authentication, schedule building, UI integration, and wiki delivery | High | High |
| 2026-02-27 | `v0.0.2-hotfix1` / `HOTFIX: Fix schedule build payload and schedule CSS` | Jostin | Best interpreted as post-release stabilization for the schedule-building flow and frontend presentation | Low | Low |
| 2026-03-01 | `Add conflict detection: show detailed time clashes when schedule build fails` | Taha Usama | Best interpreted as late ITR1 schedule-feedback hardening before the database-focused ITR2 work began | Medium | Medium |

## Iteration 2 Task Traceability

The table below records ITR2 work that can be confirmed directly from repository tags and commits.

| Date | Evidence | Primary contributor visible in Git history | Planned task allocation | Est. (h) | Actual (h) |
|---|---|---|---|---:|---:|
| 2026-03-05 | `Integrate MySQL + Flyway migrations; add course details/sections UI with term filter` | Jostin | Best interpreted as the core ITR2 database integration plus term-aware course-details flow | High | High |
| 2026-03-05 | `Add program/checklist backend endpoints + frontend integration` | Jostin | Best interpreted as delivery of the checklist and program-selection slice carried forward from the earlier planning narrative | Medium | Medium |
| 2026-03-06 | `v0.1.3` / `ITR2: DB + stub alignment, seed v3 data, fixes` | Jostin | Best interpreted as the main SQL/stub parity milestone, including store seams, stub data, and ITR2 documentation updates | High | High |
| 2026-03-06 | `ITR2-v0.1.4` / `fix stub mode by isolating SQL components and adding term store support` | Jostin | Best interpreted as stub-mode hardening and completion of term access across both runtime modes | Medium | Medium |
| 2026-03-06 | `Add comprehensive unit and integration tests and support stub/real database switching` | Wamiq Lakha | Best interpreted as the primary ITR2 testing and seam-validation pass for SQL/stub switching | High | High |
| 2026-03-06 | `added the needed documents for itr 2` | Fejuku Oyinkansola Barbara (`oyinkan` / `bubblebookmark`) | Best interpreted as the ITR2 documentation package, including planning and peer-review artifacts committed under Barbara's Git identities | Medium | Medium |
| 2026-03-07 | `ITR2-v0.1.5` / `Add unit tests and finalize ITR2 merge` | Jostin | Best interpreted as release hardening and final merge cleanup for the ITR2 delivery branch | Medium | Medium |
| 2026-03-13 | `ITR_2-v0.1.6` / authentication package reorganization and test alignment | ReubenJ | Best interpreted as late ITR2 engineering cleanup around authentication structure and database-backed auth integration | Medium | Medium |
| 2026-03-13 | `ITR2-v0.1.7` / `Added Log File in docs` | ReubenJ | Best interpreted as final ITR2 release-documentation closure rather than a new product feature | Low | Low |

## Iteration 3 Task Traceability

The table below records ITR3 work using the nearest release tags and the repository milestones they capture.

Unlike ITR1 and ITR2, the ITR3 tags were created at broader release checkpoints rather than at every individual feature commit. The evidence column therefore uses the closest release tag together with the milestone change it best represents.

As with the other Git-grounded traceability tables, the effort columns remain qualitative because no hour-based ITR3 task sheet was found in the committed repository artifacts.

| Date | Evidence | Primary contributor visible in Git history | Planned task allocation | Est. (h) | Actual (h) |
|---|---|---|---|---:|---:|
| 2026-03-22 | `ITR3-v0.1.9` / includes `2c3798e` profile-management milestone | Jostin | Best interpreted as a self-contained ITR3 feature addition focused on the authenticated user account workflow | Medium | Medium |
| 2026-03-25 | `ITR3-v0.1.10` / includes `c0f66b1` saved-course persistence milestone | Wamiq Lakha | Best interpreted as work advancing the carried-forward save-planning theme from the earlier plan | Medium | Medium |
| 2026-03-25 | `ITR3-v0.1.10` / includes `36d5c9b` Summer 2027 schedule-expansion milestone | Wamiq Lakha | Best interpreted as schedule-scope expansion rather than a new product area | Medium | Medium |
| 2026-03-29 | `ITR3-v0.1.10` / includes `594b983` feature-based backend refactor state | ReubenJ | Best interpreted as a maintainability-focused engineering refactor across multiple backend domains | High | High |
| 2026-03-29 | `ITR3-v0.1.11` / `docs: finalize ITR3 release docs and docker setup` | Jostin | Best interpreted as release hardening across README, wiki, planning, architecture, and log artifacts | Medium | Medium |

---

## Current Repository Context

The following implementation details are grounded in the current repository and remain relevant release context:

- unresolved merge markers in `application-stub.properties`
- static frontend term selector despite the presence of `/api/terms`
- checklist checkbox state is local-only
- full degree-timeline planning and plan comparison remain incomplete

## Team Confirmation Still Needed

- No dedicated ITR3 planning-meeting record was found in the committed repository artifacts.
- No hour-based ITR3 estimate sheet or actual-effort sheet was found; only qualitative repository-supported sizing can be stated safely.
- No additional committed ITR3 user stories beyond the ones documented above were found in the repository history reviewed for this release.
