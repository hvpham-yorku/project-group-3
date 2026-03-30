/**
 * Course search and term-selection panel for the dashboard.
 *
 * This component owns debounced course lookup, on-demand details loading, and
 * the add/remove actions that feed the selected-course workflow.
 */
import React, { useEffect, useMemo, useState } from "react";
import { searchCourses, getCourseDetails } from "../../api/CourseApi.js";
import {
  buildSelectedCourseKey,
  parseSelectedTerm,
} from "../../utils/selectedCourseKey.js";

/**
 * Renders the search UI for browsing courses within the selected term.
 */
export default function CourseSearch({
  selectedSet,
  savedCourseTermByCode,
  onToggle,
  selectedTerm,
  setSelectedTerm,
  selectedCount,
  onBuildSchedule,
  saving,
  saveError,
}) {
  const [q, setQ] = useState("");
  const [results, setResults] = useState([]);

  const [expanded, setExpanded] = useState({});
  const [detailsByCode, setDetailsByCode] = useState({});
  const [loadingByCode, setLoadingByCode] = useState({});

  // The backend details and search endpoints expect term data as separate season/year values.
  const { season, year } = useMemo(() => parseSelectedTerm(selectedTerm), [selectedTerm]);

  // ✅ IMPORTANT FIX: when term changes, reset cached details + open states
  // so "More Info" fetches again with the new season/year.
  useEffect(() => {
    setExpanded({});
    setDetailsByCode({});
    setLoadingByCode({});
  }, [season, year]);

  useEffect(() => {
    // Debouncing keeps the search responsive without firing a request on every keystroke.
    const t = setTimeout(async () => {
      try {
        const data = await searchCourses(q, season, year);
        setResults(Array.isArray(data) ? data : []);
      } catch (e) {
        console.error("searchCourses failed:", e);
        setResults([]);
      }
    }, 250);

    return () => clearTimeout(t);
  }, [q, season, year]);

  /**
   * Expands or collapses course details and lazily loads details the first time
   * a course is opened for the current term.
   */
  async function toggleInfo(courseCode) {
    const next = !expanded[courseCode];
    setExpanded((prev) => ({ ...prev, [courseCode]: next }));

    if (next && !detailsByCode[courseCode]) {
      setLoadingByCode((prev) => ({ ...prev, [courseCode]: true }));
      try {
        const d = await getCourseDetails(courseCode, season, year);
        setDetailsByCode((prev) => ({ ...prev, [courseCode]: d }));
      } catch (e) {
        setDetailsByCode((prev) => ({
          ...prev,
          [courseCode]: { error: e.message || "Failed to load details" },
        }));
      } finally {
        setLoadingByCode((prev) => ({ ...prev, [courseCode]: false }));
      }
    }
  }

  return (
    <div className="card dashboardPanel courseSearchCard">
      <h2>Course Search</h2>

      <label style={{ marginBottom: 10, display: "block" }}>
        Term
        <select
          value={selectedTerm}
          onChange={(e) => setSelectedTerm(e.target.value)}
        >
          <option value="FALL 2026">FALL 2026</option>
          <option value="WINTER 2027">WINTER 2027</option>
          <option value="SUMMER 2027">SUMMER 2027</option>
        </select>
      </label>

      <input
        value={q}
        onChange={(e) => setQ(e.target.value)}
        placeholder="Search e.g. EECS, MATH..."
      />

      <div style={{ marginTop: 12 }}>
        <button
          className="btn primary"
          disabled={selectedCount === 0 || saving}
          onClick={onBuildSchedule}
        >
          Build Schedule
        </button>
      </div>

      {saveError && (
        <div className="error" style={{ marginTop: 10 }}>
          {saveError}
        </div>
      )}

      <div className="list courseSearchList">
        {results.slice(0, 15).map((c) => {
          const code = c.courseCode;
          const selectionKey = buildSelectedCourseKey(selectedTerm, code);
          // A course can be saved elsewhere, so the search UI explains why add is disabled.
          const savedInTerm = savedCourseTermByCode[code] || "";
          const savedElsewhere = Boolean(savedInTerm) && savedInTerm !== selectedTerm;
          const isOpen = !!expanded[code];
          const details = detailsByCode[code];
          const loading = !!loadingByCode[code];

          return (
            <div key={code} className="row" style={{ display: "block" }}>
                <div style={{ display: "flex", justifyContent: "space-between", gap: 8 }}>
                  <div>
                    <b>{code}</b> {c.title}
                    <div className="muted" style={{ marginTop: 4 }}>
                      {c.description || "No description."}
                    </div>
                  </div>

                <div style={{ display: "flex", gap: 8 }}>
                  <button className="btn" onClick={() => toggleInfo(code)}>
                    {isOpen ? "Hide Info" : "More Info"}
                  </button>

                  <button
                    className={`btn ${selectedSet.has(selectionKey) ? "danger" : ""}`}
                    disabled={saving || savedElsewhere}
                    onClick={() => onToggle(code)}
                  >
                    {selectedSet.has(selectionKey)
                      ? "Remove"
                      : savedElsewhere
                        ? `Saved in ${savedInTerm}`
                        : "Add"}
                  </button>
                </div>
              </div>

              {isOpen && (
                <div className="card inner courseDetailsPanel" style={{ marginTop: 10 }}>
                  <div className="muted" style={{ marginBottom: 8 }}>
                    Term: <b>{season} {year}</b>
                  </div>

                  {loading && <div className="muted">Loading...</div>}
                  {!loading && details?.error && <div className="error">{details.error}</div>}

                  {!loading && details && !details.error && (
                    <>
                      <div style={{ marginBottom: 10 }}>
                        <b>Description:</b>
                        <div className="muted">{details.description || c.description || "No description."}</div>
                      </div>

                      <div>
                        <b>Sections:</b>
                        {Array.isArray(details.sections) && details.sections.length > 0 ? (
                          <div className="courseSectionsList" style={{ marginTop: 8 }}>
                            {details.sections.map((s) => (
                              <div key={s.sectionCode} className="card inner">
                                <b>Section {s.sectionCode}</b>

                                {Array.isArray(s.meetings) && s.meetings.length > 0 ? (
                                  <ul style={{ marginTop: 6 }}>
                                    {s.meetings.map((m, idx) => (
                                      <li key={`${m.day}-${m.startTime}-${idx}`}>
                                        <b>{m.day}</b> {m.startTime}–{m.endTime}{" "}
                                        <span className="muted">{m.location || ""}</span>
                                      </li>
                                    ))}
                                  </ul>
                                ) : (
                                  <div className="muted">No meeting times for this section.</div>
                                )}
                              </div>
                            ))}
                          </div>
                        ) : (
                          <div className="muted" style={{ marginTop: 6 }}>
                            No sections found for this term.
                          </div>
                        )}
                      </div>
                    </>
                  )}
                </div>
              )}
            </div>
          );
        })}

        {q.trim() !== "" && results.length === 0 && <div className="muted">No results.</div>}
      </div>
    </div>
  );
}
