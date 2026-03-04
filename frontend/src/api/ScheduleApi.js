import { http } from "./Http";

export async function buildSchedule(term, courseCodes) {
  // Protected endpoint: POST /api/schedule/build
  // body: { term: string, courseCodes: string[] }
  return http("POST", "/api/schedule/build", {
    term,
    courseCodes: Array.isArray(courseCodes) ? courseCodes : [],
  });
}