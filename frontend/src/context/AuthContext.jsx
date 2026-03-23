import React, { createContext, useContext, useEffect, useMemo, useState } from "react";
import {
  login as apiLogin,
  register as apiRegister,
} from "../api/AuthApi.js";
import { getProfile } from "../api/ProfileApi.js";
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
  const [profile, setProfile] = useState(null);
  const [profileLoaded, setProfileLoaded] = useState(false);

  const isAuthed = useMemo(() => Boolean(token), [token]);

  useEffect(() => {
    if (!token) {
      setProfile(null);
      setProfileLoaded(false);
      return undefined;
    }

    let ignore = false;
    setProfileLoaded(false);

    async function loadProfile() {
      try {
        const data = await getProfile();
        if (!ignore) {
          setProfile(data);
        }
      } catch {
        if (!ignore) {
          setProfile(null);
        }
      } finally {
        if (!ignore) {
          setProfileLoaded(true);
        }
      }
    }

    loadProfile();
    return () => {
      ignore = true;
    };
  }, [token]);

  async function login(username, password) {
    const res = await apiLogin(username, password);
    setAuth(res.token, res.username);
    setToken(res.token);
    setUsername(res.username);
    setProfile(null);
    setProfileLoaded(false);
  }

  async function register(payload) {
    const res = await apiRegister(payload);
    setAuth(res.token, res.username);
    setToken(res.token);
    setUsername(res.username);
    setProfile(null);
    setProfileLoaded(false);
  }

  async function refreshProfile() {
    if (!getToken()) {
      setProfile(null);
      setProfileLoaded(false);
      return null;
    }

    const data = await getProfile();
    setProfile(data);
    setProfileLoaded(true);
    return data;
  }

  function syncProfile(nextProfile) {
    setProfile(nextProfile || null);
    setProfileLoaded(Boolean(nextProfile));

    if (nextProfile?.email) {
      setUsername(nextProfile.email);
      setAuth(token, nextProfile.email);
    }
  }

  function logout() {
    clearAuth();
    setToken("");
    setUsername("");
    setProfile(null);
    setProfileLoaded(false);
  }

  const value = {
    token,
    username,
    profile,
    profileLoaded,
    isAuthed,
    login,
    register,
    refreshProfile,
    syncProfile,
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
