export default function TopBar({isAuthed, username, onLogout}) {
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
