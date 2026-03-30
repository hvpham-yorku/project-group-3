/**
 * Program checklist API helpers.
 *
 * This module wraps the authenticated checklist endpoint used by the dashboard.
 */
import { http } from "./Http.js";

/**
 * Returns the checklist for the currently authenticated user's program.
 */
export async function getMyChecklist() {
  return http("GET", "/api/me/checklist");
}
