import { http, setAuth, clearAuth } from "./Http.js";

/* ---------------- Auth Routes ---------------- */

// UI calls this with email, but backend still expects { username, password }
export async function login(email, password) {
  const data = await http("POST", "/api/authentication/login", {
    email: email.toLowerCase(),
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

export function logout() {
  clearAuth();
}