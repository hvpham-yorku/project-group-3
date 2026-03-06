import React from "react";
import Dashboard from "./pages/Dashboard.jsx";
import AuthPage from "./pages/AuthPage.jsx";
import { useAuth } from "./context/AuthContext.jsx";

export default function App() {
  const { isAuthed } = useAuth();

  return isAuthed ? <Dashboard /> : <AuthPage />;
}
