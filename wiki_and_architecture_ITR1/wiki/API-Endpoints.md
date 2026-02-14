# API Endpoints

> Note: exact routes may vary slightly depending on implementation. Update this page as endpoints evolve.

## Auth
- `POST /api/auth/login`
  - body: `{ "username": "...", "password": "..." }`
  - returns: `{ "token": "...", "username": "..." }`
- `POST /api/auth/register`
  - body: `{ "username": "...", "password": "..." }`
  - returns: `{ "token": "...", "username": "..." }`

## Courses
- `GET /api/courses` (protected)
  - returns: list of courses
- `GET /api/search/courses?q=EECS`
  - returns: filtered course list (public search)

## Schedule
- `POST /api/schedule/build` (protected)
  - body: `{ "term": "FALL 2026", "courseCodes": ["EECS2030", "MATH..."] }`
  - returns: `{ "term": "...", "chosenSections": [...] }`

## Notes for debugging
- If `/api/courses` fails in the UI with proxy errors, the backend is not reachable.
- If schedule build returns 409, common causes include:
  - no sections exist for a course+term
  - schedule algorithm could not find a conflict-free combination
