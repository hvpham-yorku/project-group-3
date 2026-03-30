# Planning Document - Iteration 3 Traceability

This document explains the final Iteration 3 plan and outcome as conservatively as possible from repository evidence. No separate, detailed ITR3 planning sheet with confirmed estimates was found in the committed documentation, so this file reconstructs the iteration using:

- the preserved ITR2 planning document
- the project log
- the current repository structure
- Git history and commit messages

Where a planning or cost detail cannot be verified as a historical fact, this document now uses one of two safer alternatives:

- an explicit note that the detail is not documented in the committed repository artifacts
- a qualitative repository-supported inference such as `Low`, `Medium`, or `High`

## 1. Relationship To The ITR2 Plan

The preserved ITR2 planning document shows four long-running themes:

1. term schedule building
2. full degree timeline planning
3. saving planning work
4. eligibility or rule feedback

Iteration 3 did not replace those themes; it narrowed and clarified them.

## 2. Revised ITR3 Scope Based On Repository Evidence

The following work items are clearly supported by the final repository state.

### User-Facing Stories Confirmed By The Codebase

1. As an authenticated student, I can manage my profile, program, and password.
   Evidence:
   - frontend profile page
   - `AuthController` profile endpoints
   - `UserProfileService`
   - commit `2c3798e Add profile management and polished account navigation`

2. As an authenticated student, I can save selected courses for a specific term and reload them later.
   Evidence:
   - `SavedCourseSelectionController`
   - `SavedCourseSelectionService`
   - frontend selected-course APIs
   - commit `c0f66b1 Persist saved course selections by term`

3. As a student, I can build schedules for the seeded supported terms, including Summer 2027.
   Evidence:
   - `ScheduleControllerDb`
   - `ScheduleService`
   - Summer 2027 migration and tests
   - commits `36d5c9b` and `eb30702`

4. As a student, I can browse courses, inspect term-specific section details, and use those results during schedule building.
   Evidence:
   - course catalog controllers and stores
   - frontend course search and more-info flow
   - existing SQL and stub store structure

5. As a student, I can view the checklist for my selected program.
   Evidence:
   - `ChecklistController`
   - `UserChecklistService`
   - `ProgramChecklist.jsx`

### Engineering Work Confirmed By The Repository

1. Refactor the backend to a feature-based package structure.
   Evidence:
   - commits `5a5fbbd`, `9f4be53`, `594b983`

2. Keep explicit store seams for SQL-backed and stub-backed behavior.
   Evidence:
   - feature-scoped store interfaces and implementations
   - SQL-backed integration tests

3. Maintain explicit separation between unit and integration tests.
   Evidence:
   - `backend/src/test/README_TESTS.txt`
   - integration store test layout

## 3. Planned Versus Actual Work

Because a detailed ITR3 estimate sheet was not found, the table below distinguishes confirmed delivery from missing planning metadata. The cost columns use qualitative sizing rather than invented hour totals.

| Work item | Planned status from prior iteration context | Actual Iteration 3 status | Evidence | Planned cost | Actual cost |
|---|---|---|---|---|---|
| Build a term schedule | Carried forward from ITR2 | Delivered and expanded to Summer 2027 | schedule controllers, services, migrations, tests | High - core product goal carried forward from the earlier plan | High - multiple backend changes, SQL seed updates, and test coverage confirm substantial implementation work |
| Plan a full degree timeline | Carried forward from ITR2 | Not delivered as a standalone timeline workflow | no separate degree timeline UI or API | High - large earlier ambition, but no committed ITR3 estimate sheet was found | Low - no separate implementation beyond adjacent checklist and term-selection support is confirmed |
| Save planning work | Carried forward from ITR2 | Partially delivered as saved selected courses by term | selected-course endpoints and dashboard behavior | Medium - still in scope after ITR2, but with narrower delivery than full plan persistence | Medium - delivered as per-term saved selected courses rather than as full plan management |
| Compare plans | Already reduced in ITR2 plan | Not implemented | no compare-plan UI or API found | Low - already de-scoped in the preserved ITR2 planning narrative | None confirmed - no compare-plan implementation is present in the repository |
| Eligibility or rule feedback | Shifted to checklist model in ITR2 | Delivered as a checklist view | checklist controller, service, frontend panel | Medium - reframed around checklist behavior in the earlier plan | Medium - implemented as checklist retrieval and checklist UI rather than as an advising engine |
| Profile management | New ITR3 capability visible in repo history | Delivered | profile page and profile endpoints | Medium - repository history supports a standalone ITR3 feature addition, but no separate estimate document was found | Medium - dedicated frontend page plus backend profile and password endpoints indicate a contained but non-trivial feature |
| Feature-based backend refactor | New engineering work visible in repo history | Delivered | refactor commits and final package structure | High - broad structural change across multiple backend domains | High - package moves, new feature boundaries, and corresponding test updates indicate substantial engineering work |

## 4. Deviations From The Earlier Plan

The final release deviates from the earlier planning narrative in several important ways:

1. Full degree-timeline planning was not completed.
   The repository does not contain a separate multi-term degree roadmap planner. The closest delivered feature is the program checklist.

2. Plan comparison was not implemented.
   The ITR2 planning snapshot already suggested that comparison had been dropped, and the final codebase still shows no compare-plan workflow.

3. Rule feedback is checklist-based rather than recommendation-based.
   The repository supports a checklist view of program requirements, not an advanced advising engine that tells the user what to take next.

4. Iteration 3 added engineering and account-management work not reflected in the preserved ITR2 planning text.
   The feature-based backend refactor and profile-management flow are clear repository additions.

## 5. Developer Task Traceability

The following tasks can be tied to specific repository evidence. Since no hour-based ITR3 task sheet was found, the effort columns use qualitative sizing grounded in repository scope.

| Date | Repository evidence | Primary contributor visible in Git history | Planning linkage | Estimated time | Actual time |
|---|---|---|---|---|---|
| 2026-03-22 | `Add profile management and polished account navigation` | Jostin | expands authenticated user workflow | Medium - likely planned as a self-contained ITR3 feature increment | Medium - frontend page, backend endpoints, and shared auth/profile updates suggest a contained multi-file task |
| 2026-03-25 | `Persist saved course selections by term` | Wamiq Lakha | advances saved planning work | Medium - directly supports the carried-forward save-work theme | Medium - controller, service, repo, and frontend persistence behavior all changed |
| 2026-03-25 | `Seed sections for all courses in summer 2027 and improve schedule errors` | Wamiq Lakha | extends scheduling scope | Medium - extends an existing feature rather than creating a new product area | Medium - SQL migration, schedule behavior, and tests confirm meaningful implementation effort |
| 2026-03-29 | `refactored backend... packaged by feature` and merge `RJ_RE-Organize` | ReubenJ | engineering refactor supporting maintainability | High - broad design-level change across backend packages | High - the final codebase shows large-scale structural movement rather than a localized edit |
| 2026-03-29 | `docs: document backend and frontend modules for ITR3` | Jostin | release documentation hardening | Medium - release hardening task spanning multiple docs | Medium - README, wiki, planning, architecture, and log updates indicate a moderate documentation pass |

## 6. Confirmed Unresolved Items At Final Release

The final release still contains visible issues that should be treated as open:

1. Stub profile configuration conflict markers
   `application-stub.properties` still contains unresolved merge markers.

2. Static term picker in the frontend
   The backend provides `/api/terms`, but the dashboard still hard-codes the available term list.

3. Checklist completion state is not persisted
   The current checklist checkbox state is local-only in the frontend.

4. Earlier planning ambitions remain only partially implemented
   Full degree-timeline planning and compare-plan workflows are still absent from the released system.

## 7. Team Confirmation Still Needed

- No dedicated ITR3 planning-meeting record was found in the committed repository artifacts.
- No hour-based ITR3 estimate sheet or actual-effort sheet was found; only qualitative repository-supported sizing can be stated safely.
- No additional committed ITR3 user stories beyond the ones documented above were found in the repository history reviewed for this release.
