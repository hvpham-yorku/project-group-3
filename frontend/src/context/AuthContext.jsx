/**
 * Shared authentication context for the frontend.
 *
 * This module owns session persistence, profile hydration, and the auth
 * actions consumed across pages and components. It is the main bridge between
 * backend auth APIs and the rest of the React tree.
 */
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

/**
 * Provides authentication state and auth-related actions to the application.
 */
export function AuthProvider({ children }) {
  // Stored auth is rehydrated on first render so refreshes keep the session alive.
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

    // Once a token exists, eagerly load the profile used by navigation and account UI.
    async function loadProfile() {
      try {
        const data = await getProfile();
        if (!ignore) {
          setProfile(data);
        }
      } catch {
        if (!ignore) {
          // Token is missing/expired/invalid for protected endpoints.
          // Clear auth so UI returns to login instead of showing repeated 403s.
          clearAuth();
          setToken("");
          setUsername("");
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

  /**
   * Authenticates an existing user and synchronizes local auth storage.
   */
  async function login(username, password) {
    const res = await apiLogin(username, password);
    setAuth(res.token, res.username);
    setToken(res.token);
    setUsername(res.username);
    setProfile(null);
    setProfileLoaded(false);
  }

  /**
   * Registers a new user and stores the returned authenticated session.
   */
  async function register(payload) {
    const res = await apiRegister(payload);
    setAuth(res.token, res.username);
    setToken(res.token);
    setUsername(res.username);
    setProfile(null);
    setProfileLoaded(false);
  }

  /**
   * Re-fetches the authenticated user's profile from the backend.
   */
  async function refreshProfile() {
    if (!getToken()) {
      setProfile(null);
      setProfileLoaded(false);
      return null;
    }

    try {
      const data = await getProfile();
      setProfile(data);
      setProfileLoaded(true);
      return data;
    } catch (e) {
      clearAuth();
      setToken("");
      setUsername("");
      setProfile(null);
      setProfileLoaded(false);
      throw e;
    }
  }

  /**
   * Applies a freshly updated profile to context and keeps the stored username
   * aligned with the backend's canonical email value.
   */
  function syncProfile(nextProfile) {
    setProfile(nextProfile || null);
    setProfileLoaded(Boolean(nextProfile));

    if (nextProfile?.email) {
      setUsername(nextProfile.email);
      setAuth(token, nextProfile.email);
    }
  }

  /**
   * Clears the authenticated session from both memory and persistent storage.
   */
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
/**
 * Returns the shared authentication context.
 */
export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider");
  }
  return context;
}
