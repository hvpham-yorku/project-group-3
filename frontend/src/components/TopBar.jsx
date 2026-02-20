import React from "react";

/**
 * Topbar.jsx
 * Displays:
 * - App brand
 * - Authentication status
 * - Logout button (if signed in)
 *
 * Props:
 *   isAuthed: boolean
 *   username: string
 *   onLogout: function
 */
export default function Topbar({ isAuthed, username, onLogout }) {
  return (
    <header className="topbar">
      <div className="brand">YU Path Builder</div>

      <div className="right">
        {isAuthed ? (
          <>
            <span className="muted">Signed in as</span> <b>{username}</b>
            <button className="btn" onClick={onLogout}>
              Logout
            </button>
          </>
        ) : (
          <span className="muted">Not signed in</span>
        )}
      </div>
    </header>
  );
}
