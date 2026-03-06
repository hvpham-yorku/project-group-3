# How to Run (ITR2)

## Prereqs
- **Node.js** (for Vite frontend)
- **Java** (project uses Java 25)

## Modes
The backend supports two modes:
- **SQL mode** (real MySQL + Flyway)
- **STUB mode** (no DB; JSON stub data)

Database setup instructions live in the top-level `database/` folder:
- `database/README-TA.md` (Docker)
- `database/README-TEAM.md` (Local MySQL)

---

## 1) Start the backend (SQL mode)

Open a **new** terminal and run:

### Windows
```bat
cd backend
.\mvnw.cmd spring-boot:run
```

### macOS/Linux
```bash
cd backend
./mvnw spring-boot:run
```

Expected: the backend prints something like **"Tomcat started on port ..."**.

Flyway migrations run automatically from:
- `backend/src/main/resources/db/migration/`

---

## 2) Start the backend (STUB mode — no DB)

STUB mode runs the backend without MySQL using stub JSON data under:
- `backend/src/main/resources/stub-data/`

### Windows
```bat
cd backend
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=stub
```

### macOS/Linux
```bash
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=stub
```

---

## 3) Start the frontend (Vite)

Open **another** terminal and run:

```bat
cd frontend
npm install
npm run dev
```

Then open the URL shown by Vite, usually:
- `http://localhost:5173/`

---

## Common issues

### Vite proxy ECONNREFUSED
If Vite shows proxy errors for `/api/...`, the backend is not running or is running on a different port.
- Start the backend first
- Confirm the backend port matches `vite.config.js`

### Rebuild after changing backend resources
If you edit resource files used by the backend, restart the backend.

---

## Switching persistence (one line)
The data source is selected via configuration:
- `app.store=sql` (default)
- `app.store=stub`

In practice, STUB mode is run via the `stub` Spring profile.
