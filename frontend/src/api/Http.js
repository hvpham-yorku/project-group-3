/**
 * Shared HTTP and auth-storage utilities for the frontend.
 *
 * This module centralizes token persistence and request behavior so API files
 * can stay focused on endpoint-specific concerns.
 */
const TOKEN_KEY = "ypb_token";
const USER_KEY = "ypb_user";

// An empty base path assumes either same-origin deployment or a dev proxy.
const API_BASE = "";

/* ---------------- Auth Storage ---------------- */

/**
 * Returns the persisted JWT, or an empty string when no session exists.
 */
export function getToken() {
  return localStorage.getItem(TOKEN_KEY) || "";
}

/**
 * Returns the persisted username/email associated with the current session.
 */
export function getUsername() {
  return localStorage.getItem(USER_KEY) || "";
}

/**
 * Persists the active session so the app can restore auth state after reloads.
 */
export function setAuth(token, username) {
  localStorage.setItem(TOKEN_KEY, token || "");
  localStorage.setItem(USER_KEY, username || "");
}

/**
 * Removes all persisted authentication state.
 */
export function clearAuth() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USER_KEY);
}

/* ---------------- HTTP Wrapper ---------------- */

/**
 * Sends a JSON request to the backend and normalizes both success and error
 * payloads for the rest of the frontend.
 */
export async function http(method, path, body) {
  const token = getToken();

  const res = await fetch(`${API_BASE}${path}`, {
    method,
    headers: {
      "Content-Type": "application/json",
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    },
    body: body ? JSON.stringify(body) : undefined,
  });

  const contentType = res.headers.get("content-type") || "";
  const isJson = contentType.includes("application/json");

  const payload = isJson
    ? await res.json().catch(() => null)
    : await res.text().catch(() => "");

  if (!res.ok) {
    // Prefer server-provided messages so UI surfaces meaningful backend errors.
    const msg =
      (payload && (payload.message || payload.error)) ||
      (typeof payload === "string" && payload) ||
      `HTTP ${res.status}`;
    throw new Error(msg);
  }

  return payload;
}
