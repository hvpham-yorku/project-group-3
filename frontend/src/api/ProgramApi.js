import { http } from "./Http.js";

export async function listFaculties() {
  return http("GET", "/api/faculties");
}

export async function listPrograms(facultyId) {
  const q = facultyId ? `?facultyId=${encodeURIComponent(facultyId)}` : "";
  return http("GET", `/api/programs${q}`);
}