import React, { useState } from "react";
import TopBar from "../components/layout/TopBar.jsx";
import LoginCard from "../components/auth/LoginCard.jsx";
import Register from "../components/auth/RegisterCard.jsx";
import { useAuth } from "../context/AuthContext.jsx";

export default function AuthPage() {
  const { login, register } = useAuth();
  const [mode, setMode] = useState("login");

  return (
    <>
      <TopBar/>

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
