/**
 * Schedule-building API helpers.
 *
 * This module wraps the backend endpoint that constructs a schedule from the
 * user's selected term and course list.
 */
import { http } from "./Http";

/**
 * Requests a built schedule for the supplied term and selected course codes.
 */
export async function buildSchedule(term, courseCodes) {
  // Protected endpoint: POST /api/schedule/build
  // body: { term: string, courseCodes: string[] }
  return http("POST", "/api/schedule/build", {
    term,
    courseCodes: Array.isArray(courseCodes) ? courseCodes : [],
  });
}
