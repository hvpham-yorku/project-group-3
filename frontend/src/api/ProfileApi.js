/**
 * User profile API helpers.
 *
 * This module owns the authenticated requests used by the profile page for
 * loading and updating account information.
 */
import { http } from "./Http.js";

/**
 * Returns the current authenticated user's profile.
 */
export async function getProfile() {
  return http("GET", "/api/authentication/profile");
}

/**
 * Persists profile field updates for the current user.
 */
export async function updateProfile(payload) {
  return http("PUT", "/api/authentication/profile", payload);
}

/**
 * Sends a password change request for the current user.
 */
export async function changePassword(payload) {
  return http("PUT", "/api/authentication/profile/password", payload);
}
