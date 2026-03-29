import { http } from "./Http";

export async function listSelectedCourses() {
  return http("GET", "/api/me/selected-courses");
}

export async function addSelectedCourse(term, courseCode) {
  return http("POST", "/api/me/selected-courses", { term, courseCode });
}

export async function removeSelectedCourse(term, courseCode) {
  const params = new URLSearchParams({ term, courseCode });
  return http("DELETE", `/api/me/selected-courses?${params.toString()}`);
}
