import React, { useState } from "react";

/**
 * LoginCard.jsx
 * Handles login only.
 *
 * Parent must pass:
 *   onLogin(username, password)
 */
export default function LoginCard({ onLogin }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showPass, setShowPass] = useState(false);
  const [msg, setMsg] = useState("");

  async function submit(e) {
    e.preventDefault();
    setMsg("");

    const u = username.trim();
    if (!u) return setMsg("Please enter a username.");
    if (!password) return setMsg("Please enter a password.");

    try {
      await onLogin(u, password);
    } catch (err) {
      setMsg(err.message || "Login failed");
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
            <h2 className="authTitle">Welcome back</h2>
            <div className="muted authSubtitle">
              Sign in to build schedules and plan your term.
            </div>
          </div>
        </div>

        <form onSubmit={submit} className="form authForm">
          <label>
            Username
            <input
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              autoComplete="username"
              placeholder="Enter your username"
            />
          </label>

          <label>
            Password
            <div className="authPassWrap">
              <input
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                type={showPass ? "text" : "password"}
                autoComplete="current-password"
                placeholder="Enter your password"
              />

              <button
                type="button"
                className="btn authPassBtn"
                onClick={() => setShowPass((v) => !v)}
              >
                {showPass ? "Hide" : "Show"}
              </button>
            </div>
          </label>

          {msg && <div className="error">{msg}</div>}

          <button className="btn primary authSubmit" type="submit">
            Login
          </button>
        </form>
      </div>
    </div>
  );
}
