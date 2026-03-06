import React, { useState } from "react";

/**
 * LoginCard.jsx
 * Parent must pass:
 *   onLogin(email, password)
 */
export default function LoginCard({ onLogin }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPass, setShowPass] = useState(false);
  const [msg, setMsg] = useState("");

  async function submit(e) {
    e.preventDefault();
    setMsg("");

    const e2 = email.trim();
    if (!e2) return setMsg("Please enter an email.");
    if (!password) return setMsg("Please enter a password.");

    try {
      await onLogin(e2, password);
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
            Email
            <input
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              autoComplete="username"
              placeholder="Enter your email"
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