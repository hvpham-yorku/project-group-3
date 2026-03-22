import { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext.jsx";

export default function TopBar({
  theme = "dark",
  onToggleTheme,
  showOfficeChrome = true,
  showOfficeNav = true,
  stickyOfficeChrome = true,
}) {
  const { isAuthed, username, logout } = useAuth();
  const [isPastTopbar, setIsPastTopbar] = useState(false);

  useEffect(() => {
    if (!showOfficeChrome || !stickyOfficeChrome) {
      setIsPastTopbar(false);
      return undefined;
    }

    function onScroll() {
      setIsPastTopbar(window.scrollY > 64);
    }

    onScroll();
    window.addEventListener("scroll", onScroll, { passive: true });
    return () => window.removeEventListener("scroll", onScroll);
  }, [showOfficeChrome, stickyOfficeChrome]);

  const headerClass = !showOfficeChrome
    ? "siteHeaderSimple"
    : !stickyOfficeChrome
      ? "siteHeaderAuth"
      : isPastTopbar
        ? "siteHeaderCollapsed"
        : "siteHeaderExpanded";

  return (
    <header className={`siteHeader ${headerClass}`}>
      <div className="topbar">
        <div className="brandBlock">
          <div className="wordmark" aria-label="York University">
            <span className="wordmarkYork">YORK</span>
            <span className="wordmarkU">U</span>
          </div>
        </div>

        <div className="right">
          <button className="btn themeToggle" onClick={onToggleTheme}>
            {theme === "dark" ? "Light Mode" : "Dark Mode"}
          </button>

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
      </div>

      {showOfficeChrome && (
        <div className={`officeChrome ${stickyOfficeChrome ? "" : "officeChromeInline"}`}>
          <div className="officeBar">YU Path Builder</div>
          {showOfficeNav && (
            <nav className="officeNav" aria-label="Registrar navigation">
              <span className="officeLink">User Profile</span>
              <span className="officeLink officeLinkActive">Build My Schedule</span>
              <span className="officeLink">Contact</span>
            </nav>
          )}
        </div>
      )}
    </header>
  );
}
