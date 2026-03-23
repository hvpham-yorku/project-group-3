import { http } from "./Http.js";

export async function getProfile() {
  return http("GET", "/api/authentication/profile");
}

export async function updateProfile(payload) {
  return http("PUT", "/api/authentication/profile", payload);
}

export async function changePassword(payload) {
  return http("PUT", "/api/authentication/profile/password", payload);
}
