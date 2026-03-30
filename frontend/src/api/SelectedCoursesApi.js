/**
 * Saved-course selection API helpers.
 *
 * This module wraps the authenticated endpoints used to persist the user's
 * selected courses between sessions.
 */
import { http } from "./Http";

/**
 * Returns the current user's saved course selections.
 */
export async function listSelectedCourses() {
  return http("GET", "/api/me/selected-courses");
}

/**
 * Saves a course selection for the given term.
 */
export async function addSelectedCourse(term, courseCode) {
  return http("POST", "/api/me/selected-courses", { term, courseCode });
}

/**
 * Removes a previously saved course selection.
 */
export async function removeSelectedCourse(term, courseCode) {
  const params = new URLSearchParams({ term, courseCode });
  return http("DELETE", `/api/me/selected-courses?${params.toString()}`);
}
