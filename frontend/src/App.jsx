import React from "react";
import Dashboard from "./pages/Dashboard.jsx";
import AuthPage from "./pages/AuthPage.jsx";
import { useAuth } from "./context/AuthContext.jsx";
import UIButton from "./components/layout/UIButton.jsx"; // correct import

export default function App() {
  const { isAuthed } = useAuth();

  return (
    <div>
      {/* Render the main page */}
      {isAuthed ? <Dashboard /> : <AuthPage />}
      
      {/* Always show the theme toggle button */}
      <UIButton />
    </div>
  );
}