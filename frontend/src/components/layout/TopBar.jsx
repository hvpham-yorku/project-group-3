import { useAuth } from "../../context/AuthContext.jsx";

export default function TopBar() {
  const { isAuthed, username, logout } = useAuth();

  return (
    <header className="topbar">
      <div className="brand">YU Path Builder</div>

      <div className="right">
        {isAuthed ? (
          <>
            <span className="muted">Signed in as</span> <b>{username}</b>
            <button className="btn" onClick={logout}>
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
