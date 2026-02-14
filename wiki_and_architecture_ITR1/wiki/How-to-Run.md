# How to Run (Local Dev)

## Prereqs
- **Node.js** (for Vite frontend)
- **Java** (for Spring Boot backend)

---

## 1) Start the backend (Spring Boot)

Open a **new** terminal (CMD/PowerShell) and run:

```bat
cd backend
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Expected: the backend prints something like **"Tomcat started on port ..."**.

---

## 2) Start the frontend (Vite)

Open **another** terminal and run:

```bat
cd frontend
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
If you edit resource files used by the backend, restart the backend (and re-run `clean package` if needed).
