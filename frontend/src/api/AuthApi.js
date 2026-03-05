import { http, setAuth, clearAuth } from "./Http.js";

/* ---------------- Auth Routes ---------------- */

export async function login(username, password) {
  const data = await http("POST", "/api/auth/login", {
    username,
    password,
  });

  // Save token automatically
  setAuth(data.token, data.username);

  return data;
}

export async function register(username, password) {
  const data = await http("POST", "/api/auth/register", {
    username,
    password,
  });

  setAuth(data.token, data.username);

  return data;
}

export function logout() {
  clearAuth();
}