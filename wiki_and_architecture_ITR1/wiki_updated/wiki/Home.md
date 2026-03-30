# YU Path Builder Wiki - Final Iteration 3 Release

This wiki documents the final Iteration 3 state of YU Path Builder. It preserves the progression from earlier iterations while updating the project description, architecture notes, API references, planning traceability, and known issues to match the repository as of March 29, 2026.

## Product Summary

YU Path Builder helps a York University student:

- create an account and sign in
- choose a faculty and program
- search the course catalog
- inspect term-specific course details
- save selected courses per term
- build a weekly schedule
- review the program checklist tied to the user's selected program
- manage profile information and password

## Quick Links

- Run guide: [`How-to-Run.md`](How-to-Run.md)
- API summary: [`API-Endpoints.md`](API-Endpoints.md)
- Architecture: [`Architecture.md`](Architecture.md)
- Repository structure: [`Repo-Structure.md`](Repo-Structure.md)
- Refactoring notes: [`Refactoring.md`](Refactoring.md)
- Project log: [`log.md`](log.md)
- Preserved ITR2 planning: [`../Planning_document_ITR2.md`](../Planning_document_ITR2.md)
- ITR3 planning and traceability: [`../Planning_document_ITR3.md`](../Planning_document_ITR3.md)

## What Changed In Iteration 3

The repository evidence for Iteration 3 shows four especially important changes:

1. The backend was refactored into feature-based packages.
   The final structure separates `authentication`, `course_catalog`, `program_system`, `scheduler_system`, and `system_status`.

2. Authenticated user flows expanded beyond sign-in.
   The system now includes profile retrieval, profile editing, password changes, and account navigation.

3. Course selections became persistent per term.
   The dashboard now reloads saved selections and the backend exposes `/api/me/selected-courses`.

4. The schedule domain expanded to additional terms.
   The repository contains Summer 2027 seed data and tests for that term.

## Behavior Changes Since Earlier Iterations

- The project is no longer documented as only a schedule builder; it is now a broader authenticated course-planning workflow.
- The backend is no longer best described as a flat `controller/service/repo/store` layout; it is now a feature-based structure.
- The frontend now includes a profile page and a more complete authenticated navigation flow.
- Selected courses are now treated as persisted user data instead of only transient UI state.

## Known Unresolved Issues

The final release documentation intentionally preserves important unresolved issues rather than hiding them:

1. Docker SQL setup is not aligned with the committed backend datasource port.
2. `application-stub.properties` still contains unresolved merge markers.
3. The frontend term selector is still hard-coded instead of using `/api/terms`.
4. Checklist checkbox state is not persisted.

## Documentation Strategy

This wiki does not replace the earlier project story with a clean-room rewrite. Instead, it keeps traceability across iterations:

- the ITR2 planning document is preserved
- the new ITR3 planning document explains deviations and actual outcomes
- the log distinguishes supported facts from missing historical detail
- the architecture page explains both the final design and the tested seams
