import { http } from "./Http";

export async function listTerms() {
  // GET /api/terms
  return http("GET", "/api/terms");
}