import React, { useState } from "react";
import Topbar from "../components/layout/Topbar.jsx";
import LoginCard from "../components/auth/LoginCard.jsx";
import Register from "../components/auth/RegisterCard.jsx";
import { useAuth } from "../context/AuthContext.jsx";

export default function AuthPage() {
  const { login, register } = useAuth();
  const [mode, setMode] = useState("login");

  return (
    <>
      <Topbar isAuthed={false} />

      <main className="container">
        {mode === "login" ? (
          <>
            <LoginCard onLogin={login} />
            <button onClick={() => setMode("register")}>
              Register
            </button>
          </>
        ) : (
          <>
            <Register onRegister={register} />
            <button onClick={() => setMode("login")}>
              Login
            </button>
          </>
        )}
      </main>
    </>
  );
}
