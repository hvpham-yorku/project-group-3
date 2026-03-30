/**
 * Root application shell for the frontend.
 *
 * This component coordinates top-level concerns such as theme persistence,
 * lightweight client-side navigation, and which page should be shown for the
 * current authentication state.
 */
import React, { useEffect, useState } from "react";
import Dashboard from "./pages/Dashboard.jsx";
import AuthPage from "./pages/AuthPage.jsx";
import ProfilePage from "./pages/ProfilePage.jsx";
import { useAuth } from "./context/AuthContext.jsx";

/**
 * Chooses the active page and maintains top-level UI state shared across the
 * authenticated experience.
 */
export default function App() {
  const { isAuthed } = useAuth();
  // Theme preference is persisted so the experience survives page reloads.
  const [theme, setTheme] = useState(() => localStorage.getItem("theme") || "dark");
  // Navigation is intentionally managed through the History API instead of a routing library.
  const [path, setPath] = useState(() => window.location.pathname || "/");

  useEffect(() => {
    document.body.dataset.theme = theme;
    localStorage.setItem("theme", theme);
  }, [theme]);

  useEffect(() => {
    // Keep local UI state aligned with browser back/forward navigation.
    function onPopState() {
      setPath(window.location.pathname || "/");
    }

    window.addEventListener("popstate", onPopState);
    return () => window.removeEventListener("popstate", onPopState);
  }, []);

  function toggleTheme() {
    setTheme((prev) => (prev === "dark" ? "light" : "dark"));
  }

  function navigate(nextPath) {
    const normalized = nextPath || "/";
    if (window.location.pathname !== normalized) {
      window.history.pushState({}, "", normalized);
      setPath(normalized);
    }
  }

  if (!isAuthed) {
    return (
      <>
        <AuthPage theme={theme} onToggleTheme={toggleTheme} />
        <button className="btn uiToggleFab" onClick={toggleTheme}>
          {theme === "dark" ? "Light Mode" : "Dark Mode"}
        </button>
      </>
    );
  }

  if (path === "/profile") {
    return (
      <>
        <ProfilePage theme={theme} onToggleTheme={toggleTheme} onNavigate={navigate} />
        <button className="btn uiToggleFab" onClick={toggleTheme}>
          {theme === "dark" ? "Light Mode" : "Dark Mode"}
        </button>
      </>
    );
  }

  if (path !== "/") {
    // Authenticated users only support a small set of routes in this lightweight router.
    navigate("/");
  }

  return (
    <>
      <Dashboard theme={theme} onToggleTheme={toggleTheme} onNavigate={navigate} />
      <button className="btn uiToggleFab" onClick={toggleTheme}>
        {theme === "dark" ? "Light Mode" : "Dark Mode"}
      </button>
    </>
  );
}
