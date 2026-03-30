/**
 * Academic program catalog API helpers.
 *
 * These requests support frontend flows that need faculty and program data,
 * such as registration and profile editing.
 */
import { http } from "./Http.js";

/**
 * Returns the list of faculties exposed by the backend.
 */
export async function listFaculties() {
  return http("GET", "/api/faculties");
}

/**
 * Returns all programs or only those for a selected faculty.
 */
export async function listPrograms(facultyId) {
  const q = facultyId ? `?facultyId=${encodeURIComponent(facultyId)}` : "";
  return http("GET", `/api/programs${q}`);
}
