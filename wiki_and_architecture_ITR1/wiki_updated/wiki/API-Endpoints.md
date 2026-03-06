# API Endpoints (ITR2)

> These are the main routes used by the frontend for ITR2.

## Auth
- `POST /api/auth/login`
  - body: `{ "username": "<email>", "password": "..." }`
  - returns: `{ "token": "...", "username": "..." }`

- `POST /api/auth/register`
  - body:
    ```json
    {
      "firstName": "...",
      "lastName": "...",
      "email": "...",
      "programId": 1,
      "password": "...",
      "confirmPassword": "..."
    }
    ```
  - returns: `{ "token": "...", "username": "..." }`

## Catalog (faculties/programs)
- `GET /api/faculties`
  - returns: list of faculties

- `GET /api/programs?facultyId=<id>`
  - returns: list of programs (filtered by faculty if provided)

## Checklist
- `GET /api/me/checklist` (protected)
  - returns: checklist grouped by year + group for the logged-in user

## Courses
- `GET /api/courses?q=<query>&season=<FALL|WINTER>&year=<YYYY>` (protected)
  - used by the course search UI
  - in STUB mode, `season/year` are accepted for compatibility

- `GET /api/courses/{courseCode}/details?season=<...>&year=<...>` (protected)
  - used by the “More Info” panel

## Schedule
- `POST /api/schedule/build` (protected)
  - body: `{ "term": "FALL 2026", "courseCodes": ["EECS 2311", "MATH 1013"] }`
  - returns: `{ "term": "...", "chosenSections": [...] }`

## Notes
- Protected endpoints require header:
  - `Authorization: Bearer <JWT>`
- The backend supports `app.store=sql|stub` (dependency injection) to switch between real DB and stub data.
