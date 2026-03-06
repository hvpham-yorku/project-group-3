import { http } from "./Http.js";

export async function getMyChecklist() {
  return http("GET", "/api/me/checklist");
}