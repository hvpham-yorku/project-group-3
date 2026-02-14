import React, { useMemo, useState } from "react";
import {
  clearAuth,
  getToken,
  getUsername,
  setAuth,
  login as apiLogin,
  register as apiRegister,
} from "./api.js";
import Dashboard from "./Dashboard.jsx";

/**
 * App.jsx
 * Root component:
 * - Shows a top navigation bar
 * - If not authenticated -> shows Login/Register UI
 * - If authenticated -> shows the main Dashboard (course search + schedule builder)
 */
export default function App() {
  // Initialize auth state from localStorage (so refresh keeps you signed in).
  const [token, setToken] = useState(getToken());
  const [username, setUsername] = useState(getUsername());

  const isAuthed = useMemo(() => Boolean(token), [token]);

  async function handleLogin(user, pass) {
    // Backend returns { token, username }
    const res = await apiLogin(user, pass);
    setAuth(res.token, res.username);
    setToken(res.token);
    setUsername(res.username);
  }

  async function handleRegister(user, pass /* firstName, lastName */) {
    // UI collects more fields, but backend currently only needs username/password.
    const res = await apiRegister(user, pass);
    setAuth(res.token, res.username);
    setToken(res.token);
    setUsername(res.username);
  }

  function handleLogout() {
    clearAuth();
    setToken("");
    setUsername("");
  }

  return (
    <div className="app">
      <header className="topbar">
        <div className="brand">YU Path Builder</div>

        <div className="right">
          {isAuthed ? (
            <>
              <span className="muted">Signed in as</span> <b>{username}</b>
              <button className="btn" onClick={handleLogout}>
                Logout
              </button>
            </>
          ) : (
            <span className="muted">Not signed in</span>
          )}
        </div>
      </header>

      <main className="container">
        {!isAuthed ? (
          <AuthCard onLogin={handleLogin} onRegister={handleRegister} />
        ) : (
          <Dashboard />
        )}
      </main>
    </div>
  );
}

/**
 * AuthCard
 * Single "card" that contains:
 * - Login mode
 * - Register mode
 *
 * Note: first/last name are currently UI-only. Once backend supports it,
 * we can include them in the register payload.
 */
function AuthCard({ onLogin, onRegister }) {
  const [mode, setMode] = useState("login"); // "login" | "register"
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showPass, setShowPass] = useState(false);
  const [msg, setMsg] = useState("");

  async function submit(e) {
    e.preventDefault();
    setMsg("");

    // Basic client-side validation (backend should still validate too).
    const u = username.trim();
    if (!u) return setMsg("Please enter a username.");
    if (!password) return setMsg("Please enter a password.");

    try {
      if (mode === "login") {
        await onLogin(u, password);
      } else {
        await onRegister(u, password, firstName.trim(), lastName.trim());
      }
    } catch (err) {
      setMsg(err.message || "Authentication failed");
    }
  }

  return (
    <div className="authShell">
      <div className="card authCard">
        <div className="authHeader">
          <div className="authLogo" aria-hidden="true">
            YU
          </div>

          <div>
            <h2 className="authTitle">
              {mode === "login" ? "Welcome back" : "Create your account"}
            </h2>

            <div className="muted authSubtitle">
              {mode === "login"
                ? "Sign in to build schedules and plan your term."
                : "Create an account to save and build schedules."}
            </div>
          </div>
        </div>

        {/* Tabs to switch between login/register */}
        <div className="authTabs" role="tablist" aria-label="Authentication mode">
          <button
            type="button"
            className={`authTab ${mode === "login" ? "active" : ""}`}
            onClick={() => {
              setMode("login");
              setMsg("");
            }}
          >
            Login
          </button>

          <button
            type="button"
            className={`authTab ${mode === "register" ? "active" : ""}`}
            onClick={() => {
              setMode("register");
              setMsg("");
            }}
          >
            Register
          </button>
        </div>

        <form onSubmit={submit} className="form authForm">
          {/* Register-only fields (UI only for now) */}
          {mode === "register" ? (
            <div className="authRow2">
              <label>
                First name
                <input
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                  placeholder="e.g., Jostin"
                  autoComplete="given-name"
                />
              </label>

              <label>
                Last name
                <input
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                  placeholder="e.g., Martinez"
                  autoComplete="family-name"
                />
              </label>
            </div>
          ) : null}

          <label>
            Username
            <input
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Enter your username"
              autoComplete={mode === "login" ? "username" : "new-username"}
            />
          </label>

          <label>
            Password
            <div className="authPassWrap">
              <input
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder={mode === "login" ? "Enter your password" : "Create a password"}
                type={showPass ? "text" : "password"}
                autoComplete={mode === "login" ? "current-password" : "new-password"}
              />

              <button
                type="button"
                className="btn authPassBtn"
                onClick={() => setShowPass((v) => !v)}
                aria-label={showPass ? "Hide password" : "Show password"}
              >
                {showPass ? "Hide" : "Show"}
              </button>
            </div>
          </label>

          {msg ? <div className="error">{msg}</div> : null}

          <button className="btn primary authSubmit" type="submit">
            {mode === "login" ? "Login" : "Create account"}
          </button>

          {/* Helpful link to switch modes */}
          <div className="muted tiny authFoot">
            {mode === "login" ? (
              <>
                Don&apos;t have an account?{" "}
                <button
                  className="btn link"
                  type="button"
                  onClick={() => {
                    setMode("register");
                    setMsg("");
                  }}
                >
                  Register
                </button>
              </>
            ) : (
              <>
                Already have an account?{" "}
                <button
                  className="btn link"
                  type="button"
                  onClick={() => {
                    setMode("login");
                    setMsg("");
                  }}
                >
                  Login
                </button>
              </>
            )}
          </div>
        </form>
      </div>
    </div>
  );
}
