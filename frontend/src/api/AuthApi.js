import { http, setAuth, clearAuth } from "./Http.js";

/* ---------------- Auth Routes ---------------- */

// UI calls this with email, but backend still expects { username, password }
export async function login(email, password) {
  const data = await http("POST", "/api/auth/login", {
    username: email,
    password,
  });

  setAuth(data.token, data.username);
  return data;
}

// New register payload expected by backend
export async function register({
  firstName,
  lastName,
  email,
  programId,
  password,
  confirmPassword,
}) {
  const data = await http("POST", "/api/auth/register", {
    firstName,
    lastName,
    email,
    programId,
    password,
    confirmPassword,
  });

  setAuth(data.token, data.username);
  return data;
}

export function logout() {
  clearAuth();
}