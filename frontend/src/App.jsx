import React, { useEffect, useState } from "react";
import Dashboard from "./pages/Dashboard.jsx";
import AuthPage from "./pages/AuthPage.jsx";
import ProfilePage from "./pages/ProfilePage.jsx";
import { useAuth } from "./context/AuthContext.jsx";

export default function App() {
  const { isAuthed } = useAuth();
  const [theme, setTheme] = useState(() => localStorage.getItem("theme") || "dark");
  const [path, setPath] = useState(() => window.location.pathname || "/");

  useEffect(() => {
    document.body.dataset.theme = theme;
    localStorage.setItem("theme", theme);
  }, [theme]);

  useEffect(() => {
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
    return <AuthPage theme={theme} onToggleTheme={toggleTheme} />;
  }

  if (path === "/profile") {
    return <ProfilePage theme={theme} onToggleTheme={toggleTheme} onNavigate={navigate} />;
  }

  if (path !== "/") {
    navigate("/");
  }

  return <Dashboard theme={theme} onToggleTheme={toggleTheme} onNavigate={navigate} />;
}
