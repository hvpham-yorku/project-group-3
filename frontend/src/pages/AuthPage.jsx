import React, { useState } from "react";
import TopBar from "../components/layout/TopBar.jsx";
import LoginCard from "../components/auth/LoginCard.jsx";
import RegisterCard from "../components/auth/RegisterCard.jsx";
import { useAuth } from "../context/AuthContext.jsx";

export default function AuthPage() {
  const { login, register } = useAuth();
  const [mode, setMode] = useState("login");

  return (
    <>
      <TopBar />

      <main className="container">
        <div className="authTabs">
          <button
            className={`authTab ${mode === "login" ? "active" : ""}`}
            onClick={() => setMode("login")}
          >
            Login
          </button>
          <button
            className={`authTab ${mode === "register" ? "active" : ""}`}
            onClick={() => setMode("register")}
          >
            Register
          </button>
        </div>

        {mode === "login" ? (
          <LoginCard onLogin={login} />
        ) : (
          <RegisterCard onRegister={register} />
        )}
      </main>
    </>
  );
}