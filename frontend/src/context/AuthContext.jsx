import React, { createContext, useContext, useMemo, useState } from "react";
import {
  login as apiLogin,
  register as apiRegister,
} from "../api/AuthApi.js";
import { 
  clearAuth, 
  getToken,
  getUsername,
  setAuth, 
} from "../api/Http.js";



/**
 * AuthContext
 * Centralizes:
 * - token
 * - username
 * - isAuthed
 * - login
 * - register
 * - logout
 */

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(getToken());
  const [username, setUsername] = useState(getUsername());

  const isAuthed = useMemo(() => Boolean(token), [token]);

  async function login(username, password) {
    const res = await apiLogin(username, password);
    setAuth(res.token, res.username);
    setToken(res.token);
    setUsername(res.username);
  }

  async function register(username, password, firstName, lastName) {
    const res = await apiRegister(username, password);
    setAuth(res.token, res.username);
    setToken(res.token);
    setUsername(res.username);
  }

  function logout() {
    clearAuth();
    setToken("");
    setUsername("");
  }

  const value = {
    token,
    username,
    isAuthed,
    login,
    register,
    logout,
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}

/**
 * Custom Hook
 * Cleaner access instead of useContext(AuthContext)
 */
export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider");
  }
  return context;
}
