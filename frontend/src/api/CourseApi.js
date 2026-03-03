import { http } from "./Http";

export async function searchCourses(query) {
  // Public endpoint: GET /api/search/courses?q=...
  const q = encodeURIComponent(query || "");
  return http("GET", `/api/search/courses?q=${q}`);
}

export async function listCourses() {
  // Protected endpoint: GET /api/courses
  return http("GET", "/api/courses");
}