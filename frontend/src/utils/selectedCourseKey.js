/**
 * Shared helpers for the selected-course key format used across the dashboard.
 *
 * Keys intentionally keep the existing `${SEASON}-${YEAR}-${COURSE_CODE}` shape
 * so current UI and backend interactions remain compatible.
 */
const DEFAULT_SEASON = "FALL";
const DEFAULT_YEAR = 2026;

/**
 * Parses a user-facing term label like `FALL 2026` into normalized parts.
 */
export function parseSelectedTerm(termLabel) {
  const [seasonRaw, yearRaw] = String(termLabel || "").trim().split(/\s+/);
  const season = (seasonRaw || DEFAULT_SEASON).toUpperCase();
  const parsedYear = Number.parseInt(yearRaw || String(DEFAULT_YEAR), 10);
  const year = Number.isNaN(parsedYear) ? DEFAULT_YEAR : parsedYear;

  return { season, year };
}

/**
 * Returns the stable selected-course key prefix for a term.
 */
export function buildSelectedCourseKeyPrefix(termLabel) {
  const { season, year } = parseSelectedTerm(termLabel);
  return `${season}-${year}-`;
}

/**
 * Builds the flattened selected-course key for one term/course pair.
 */
export function buildSelectedCourseKey(termLabel, courseCode) {
  return `${buildSelectedCourseKeyPrefix(termLabel)}${courseCode}`;
}

/**
 * Decodes a flattened selected-course key back into its display term and code.
 */
export function parseSelectedCourseKey(key) {
  const firstDash = key.indexOf("-");
  const secondDash = key.indexOf("-", firstDash + 1);

  if (firstDash < 0 || secondDash < 0) {
    return {
      season: "",
      year: null,
      term: "",
      courseCode: key,
    };
  }

  const season = key.slice(0, firstDash);
  const year = Number.parseInt(key.slice(firstDash + 1, secondDash), 10);
  const normalizedYear = Number.isNaN(year) ? null : year;

  return {
    season,
    year: normalizedYear,
    term: normalizedYear == null ? season : `${season} ${normalizedYear}`,
    courseCode: key.slice(secondDash + 1),
  };
}
