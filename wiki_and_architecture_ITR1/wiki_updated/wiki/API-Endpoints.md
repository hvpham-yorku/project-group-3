# API Endpoints - Final Iteration 3 Summary

This page summarizes the backend endpoints that are visible in the current Spring controllers. It is intended as a release-level reference rather than a full OpenAPI specification.

## Authentication And Profile

Base path:

- `/api/authentication`

Endpoints:

- `POST /api/authentication/register`
- `POST /api/authentication/login`
- `GET /api/authentication/me`
- `GET /api/authentication/profile`
- `PUT /api/authentication/profile`
- `PUT /api/authentication/profile/password`

Typical register payload:

```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane@yorku.ca",
  "programId": 1,
  "password": "secret123",
  "confirmPassword": "secret123"
}
```

Typical login payload:

```json
{
  "email": "jane@yorku.ca",
  "password": "secret123"
}
```

Successful authentication returns a JWT wrapper:

```json
{
  "token": "jwt-token-here",
  "username": "jane@yorku.ca"
}
```

## Faculty And Program Catalog

- `GET /api/faculties`
- `GET /api/programs`
- `GET /api/programs?facultyId=<id>`

These endpoints are used during registration and profile editing.

## Program Checklist

- `GET /api/me/checklist`

This endpoint returns the checklist for the authenticated user's current program.

## Course Catalog

Search endpoints:

- `GET /api/search/courses?q=<query>&season=<FALL|WINTER|SUMMER>&year=<YYYY>`
- `GET /api/courses?q=<query>&season=<FALL|WINTER|SUMMER>&year=<YYYY>`

Course details endpoint:

- `GET /api/courses/{courseCode}/details?season=<FALL|WINTER|SUMMER>&year=<YYYY>`

Notes:

- The codebase contains both `/api/search/courses` and `/api/courses` for course lookup.
- The frontend currently uses term-specific search and details requests.

## Terms

- `GET /api/terms`

The backend exposes this endpoint, but the current frontend term selector is still hard-coded to a fixed list of terms.

## Saved Selected Courses

Base path:

- `/api/me/selected-courses`

Endpoints:

- `GET /api/me/selected-courses`
- `POST /api/me/selected-courses`
- `DELETE /api/me/selected-courses?term=<TERM>&courseCode=<CODE>`

Create payload:

```json
{
  "term": "FALL 2026",
  "courseCode": "EECS 1011"
}
```

Response shape:

```json
{
  "term": "FALL 2026",
  "courseCode": "EECS 1011"
}
```

## Schedule Building

Base path:

- `/api/schedule`

Endpoint:

- `POST /api/schedule/build`

Typical payload:

```json
{
  "term": "SUMMER 2027",
  "courseCodes": ["EECS 1011", "MATH 1013"]
}
```

The response includes the resolved term label and the chosen sections used to render the weekly schedule grid.

## System Status

- `GET /api/ping`
- `GET /api/health`

These endpoints provide lightweight liveness and health responses.

## Authentication Requirement

Protected endpoints require:

```text
Authorization: Bearer <JWT>
```

Public endpoints are limited to registration, login, and the lightweight status endpoints.

## Important Release Notes

The endpoint list above reflects the controller mappings in the current repository. It also highlights a final-release mismatch:

- the backend exposes `/api/terms`
- the frontend currently does not consume it dynamically
