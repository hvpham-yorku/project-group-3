import React, { useState } from "react";
import TopBar from "../components/layout/TopBar.jsx";
import LoginCard from "../components/auth/LoginCard.jsx";
import RegisterCard from "../components/auth/RegisterCard.jsx";
import { useAuth } from "../context/AuthContext.jsx";
import "../index.css";
import "../styles/AuthPage.css";

export default function AuthPage() {
  const { login, register } = useAuth();
  const [mode, setMode] = useState("login");

  const toggle = mode === "login";

  return (
    <>
    <TopBar/>
      <div className="authShell">
        <div className="authShell">

            {toggle ? (
              <LoginCard onLogin={login} />
            ) : (
              <Register onRegister={register} />
            )}

            <div className="authSwitch">
                <button 
                className="btn primary authSubmit"
                onClick={() => setMode(toggle ? "register" : "login")}
                >
                  {toggle ? "Don't have an account? Register" : "Already have an account? Login"}

                </button>
            </div>

        </div>
      </div>
    </>
  );
}