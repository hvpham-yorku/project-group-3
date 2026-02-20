/**
 * api.js
 * Centralized API helpers for the YU Path Builder frontend.
 *
 * - Stores JWT + username in localStorage
 * - Adds Authorization header automatically for protected endpoints
 * - Provides wrapper functions for the backend routes used by the UI
 */

const TOKEN_KEY = "ypb_token";
const USER_KEY = "ypb_user";

// Backend base URL is handled by Vite proxy (see vite.config.js).
// We call "/api/..." from the browser and Vite forwards it to Spring Boot.
const API_BASE = "";

/* ------------------------------ Auth storage ------------------------------ */

export function getToken() {
  return localStorage.getItem(TOKEN_KEY) || "";
}

export function getUsername() {
  return localStorage.getItem(USER_KEY) || "";
}

export function setAuth(token, username) {
  localStorage.setItem(TOKEN_KEY, token || "");
  localStorage.setItem(USER_KEY, username || "");
}

export function clearAuth() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USER_KEY);
}

/* ------------------------------ HTTP helper ------------------------------ */

/**
 * Small fetch wrapper:
 * - JSON request/response
 * - attaches Authorization header if a token exists
 * - throws a readable Error for non-2xx responses
 */
async function http(method, path, body) {
  const token = getToken();

  const res = await fetch(`${API_BASE}${path}`, {
    method,
    headers: {
      "Content-Type": "application/json",
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    },
    body: body ? JSON.stringify(body) : undefined,
  });

  // Try to parse JSON error messages (if backend returns JSON)
  const contentType = res.headers.get("content-type") || "";
  const isJson = contentType.includes("application/json");
  const payload = isJson ? await res.json().catch(() => null) : await res.text().catch(() => "");

  if (!res.ok) {
    const msg =
      (payload && (payload.message || payload.error)) ||
      (typeof payload === "string" && payload) ||
      `HTTP ${res.status}`;
    throw new Error(msg);
  }

  return payload;
}

/* ------------------------------ Auth routes ------------------------------ */

export async function login(username, password) {
  // POST /api/auth/login -> { token, username }
  return http("POST", "/api/auth/login", { username, password });
}

export async function register(username, password) {
  // POST /api/auth/register -> { token, username }
  return http("POST", "/api/auth/register", { username, password });
}

/* ------------------------------ Course routes ------------------------------ */

export async function searchCourses(query) {
  // Public endpoint: GET /api/search/courses?q=...
  const q = encodeURIComponent(query || "");
  return http("GET", `/api/search/courses?q=${q}`);
}

export async function listCourses() {
  // Protected endpoint: GET /api/courses
  return http("GET", "/api/courses");
}

/* ------------------------------ Schedule routes ------------------------------ */

export async function buildSchedule(term, courseCodes) {
  // Protected endpoint: POST /api/schedule/build
  // body: { term: string, courses: string[] }
  return http("POST", "/api/schedule/build", {
    term,
    courses: Array.isArray(courseCodes) ? courseCodes : [],
  });
}
