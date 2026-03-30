# Frontend Overview

This directory contains the React + Vite client for YU Path Builder.

The frontend is responsible for:

- authentication entry screens
- authenticated navigation
- course search and course-details display
- saved selected-course management
- schedule rendering and conflict presentation
- profile management
- program checklist display

## Main Entry Points

- `src/main.jsx` bootstraps the React application
- `src/App.jsx` acts as the top-level shell and lightweight router
- `src/context/AuthContext.jsx` owns shared authentication state
- `src/pages/AuthPage.jsx` renders the sign-in and registration flow
- `src/pages/Dashboard.jsx` renders the main planning workflow
- `src/pages/ProfilePage.jsx` renders user profile management

## Running The Frontend

```bash
cd frontend
npm install
npm run dev
```

The Vite development server typically runs on `http://localhost:5173/`.

## Backend Dependency

Most frontend features require the backend to be running because the client calls authenticated API endpoints for:

- login and registration
- faculty and program lookup
- course search
- course details
- schedule building
- saved selected courses
- profile management
- checklist retrieval

For the full project run guide, see:

- [`../README.md`](../README.md)
- [`../wiki_and_architecture_ITR1/wiki_updated/wiki/How-to-Run.md`](../wiki_and_architecture_ITR1/wiki_updated/wiki/How-to-Run.md)

## Current Release Notes

The final Iteration 3 frontend includes:

- a profile page and account navigation
- schedule conflict display improvements
- persistent saved-course loading by term
- a reorganized page/component/API/context structure

Known limitation:

- The term selector is currently hard-coded in the dashboard instead of loading `/api/terms` dynamically.
