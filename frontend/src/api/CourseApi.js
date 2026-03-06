import { http } from "./Http";

export async function searchCourses(query, season, year) {
  const q = encodeURIComponent(query || "");
  const s = season ? `&season=${encodeURIComponent(season)}` : "";
  const y = year ? `&year=${encodeURIComponent(year)}` : "";
  // GET /api/search/courses?q=...&season=FALL&year=2026
  return http("GET", `/api/search/courses?q=${q}${s}${y}`);
}

export async function listCourses() {
  // GET /api/courses
  return http("GET", "/api/courses");
}

export async function getCourseDetails(courseCode, season, year) {
  const code = encodeURIComponent(courseCode);
  const s = encodeURIComponent(season);
  const y = encodeURIComponent(year);
  // GET /api/courses/{courseCode}/details?season=FALL&year=2026
  return http("GET", `/api/courses/${code}/details?season=${s}&year=${y}`);
}