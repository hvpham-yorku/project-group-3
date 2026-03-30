/**
 * Authentication API helpers.
 *
 * This module wraps the backend authentication endpoints and keeps request
 * payload shapes aligned with what the React auth flow expects.
 */
import { http, setAuth, clearAuth } from "./Http.js";

/* ---------------- Auth Routes ---------------- */

// UI calls this with email, but backend still expects { username, password }
/**
 * Sends login credentials to the backend and persists the returned session.
 */
export async function login(email, password) {
  const data = await http("POST", "/api/authentication/login", {
    email: email.toLowerCase(),
    password,
  });

  setAuth(data.token, data.username);
  return data;
}

// New register payload expected by backend
/**
 * Registers a new user and persists the authenticated session returned by the
 * backend.
 */
export async function register({
  firstName,
  lastName,
  email,
  programId,
  password,
  confirmPassword,
}) {
  const data = await http("POST", "/api/authentication/register", {
    firstName,
    lastName,
    email: email.toLowerCase(),
    programId,
    password,
    confirmPassword,
  });

  setAuth(data.token, data.username);
  return data;
}

/**
 * Clears the client-side authenticated session.
 */
export function logout() {
  clearAuth();
}
