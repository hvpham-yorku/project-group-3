const TOKEN_KEY = "ypb_token";
const USER_KEY = "ypb_user";

const API_BASE = "";

/* ---------------- Auth Storage ---------------- */

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

/* ---------------- HTTP Wrapper ---------------- */

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
    const msg =
      (payload && (payload.message || payload.error)) ||
      (typeof payload === "string" && payload) ||
      `HTTP ${res.status}`;
    throw new Error(msg);
  }

  return payload;
}