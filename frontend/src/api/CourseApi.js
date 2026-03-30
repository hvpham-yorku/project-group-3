/**
 * Course catalog API helpers.
 *
 * This module contains the frontend-to-backend requests used by course search,
 * catalog listing, and detailed section inspection flows.
 */
import { http } from "./Http";

/**
 * Searches courses using the backend course search endpoint.
 */
export async function searchCourses(query, season, year) {
  const q = encodeURIComponent(query || "");
  const s = season ? `&season=${encodeURIComponent(season)}` : "";
  const y = year ? `&year=${encodeURIComponent(year)}` : "";
  // GET /api/search/courses?q=...&season=FALL&year=2026
  return http("GET", `/api/search/courses?q=${q}${s}${y}`);
}

/**
 * Returns the default course catalog listing.
 */
export async function listCourses() {
  // GET /api/courses
  return http("GET", "/api/courses");
}

/**
 * Returns detailed course information for a specific term.
 */
export async function getCourseDetails(courseCode, season, year) {
  const code = encodeURIComponent(courseCode);
  const s = encodeURIComponent(season);
  const y = encodeURIComponent(year);
  // GET /api/courses/{courseCode}/details?season=FALL&year=2026
  return http("GET", `/api/courses/${code}/details?season=${s}&year=${y}`);
}
