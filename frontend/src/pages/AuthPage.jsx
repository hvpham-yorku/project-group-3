import React, { useState } from "react";
import TopBar from "../components/layout/TopBar.jsx";
import LoginCard from "../components/auth/LoginCard.jsx";
import RegisterCard from "../components/auth/RegisterCard.jsx";
import { useAuth } from "../context/AuthContext.jsx";
import "../index.css";
import "../styles/AuthPage.css";

export default function AuthPage({ theme, onToggleTheme }) {
  const { login, register } = useAuth();
  const [mode, setMode] = useState("login");

  const showLogin = mode === "login";

  return (
    <>
      <TopBar
        theme={theme}
        onToggleTheme={onToggleTheme}
        showOfficeChrome={true}
        showOfficeNav={false}
        stickyOfficeChrome={false}
      />

      <div className="authShell">
        <div className="authStack">
          {showLogin ? (
            <LoginCard onLogin={login} />
          ) : (
            <RegisterCard onRegister={register} />
          )}

          <div className="authSwitch">
            <button
              className="btn primary authSwitchBtn"
              onClick={() => setMode(showLogin ? "register" : "login")}
            >
              {showLogin ? "Don't have an account? Register" : "Already have an account? Login"}
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
