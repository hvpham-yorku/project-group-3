import { http } from "./http";

export async function buildSchedule(term, courseCodes) {
  return http("POST", "/api/schedule/build", {
    term,
    courses: Array.isArray(courseCodes) ? courseCodes : [],
  });
}