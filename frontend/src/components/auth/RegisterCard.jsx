import React, { useState } from "react";

/**
 * Register.jsx
 * Handles user registration.
 *
 * Parent must pass:
 *   onRegister(username, password, firstName, lastName)
 */
export default function Register({ onRegister }) {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
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
      await onRegister(
        u,
        password,
        firstName.trim(),
        lastName.trim()
      );
    } catch (err) {
      setMsg(err.message || "Registration failed");
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
            <h2 className="authTitle">Create your account</h2>
            <div className="muted authSubtitle">
              Create an account to save and build schedules.
            </div>
          </div>
        </div>

        <form onSubmit={submit} className="form authForm">
          <div className="authRow2">
            <label>
              First name
              <input
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                autoComplete="given-name"
                placeholder="e.g., Jostin"
              />
            </label>

            <label>
              Last name
              <input
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                autoComplete="family-name"
                placeholder="e.g., Martinez"
              />
            </label>
          </div>

          <label>
            Username
            <input
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              autoComplete="new-username"
              placeholder="Choose a username"
            />
          </label>

          <label>
            Password
            <div className="authPassWrap">
              <input
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                type={showPass ? "text" : "password"}
                autoComplete="new-password"
                placeholder="Create a password"
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
            Create Account
          </button>
        </form>
      </div>
    </div>
  );
}
