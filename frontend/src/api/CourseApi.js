import { http } from "./http";

export async function searchCourses(query) {
  const q = encodeURIComponent(query || "");
  return http("GET", `/api/search/courses?q=${q}`);
}

export async function listCourses() {
  return http("GET", "/api/courses");
}