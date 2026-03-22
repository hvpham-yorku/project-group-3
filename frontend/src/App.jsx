import React, { useEffect, useState } from "react";
import Dashboard from "./pages/Dashboard.jsx";
import AuthPage from "./pages/AuthPage.jsx";
import { useAuth } from "./context/AuthContext.jsx";

export default function App() {
  const { isAuthed } = useAuth();
  const [theme, setTheme] = useState(() => localStorage.getItem("theme") || "dark");

  useEffect(() => {
    document.body.dataset.theme = theme;
    localStorage.setItem("theme", theme);
  }, [theme]);

  function toggleTheme() {
    setTheme((prev) => (prev === "dark" ? "light" : "dark"));
  }

  return isAuthed ? (
    <Dashboard theme={theme} onToggleTheme={toggleTheme} />
  ) : (
    <AuthPage theme={theme} onToggleTheme={toggleTheme} />
  );
}
