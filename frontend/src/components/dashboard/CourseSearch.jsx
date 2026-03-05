import React, { useEffect, useState } from "react";
import { searchCourses, getCourseDetails } from "../../api/CourseApi.js";

export default function CourseSearch({
  selectedSet,
  onToggle,
  termSeason,
  termYear,
  setTermSeason,
  setTermYear,
}) {
  const [q, setQ] = useState("");
  const [results, setResults] = useState([]);

  // Expand/collapse per course
  const [expanded, setExpanded] = useState({}); // { "EECS 2311": true }
  const [detailsByCode, setDetailsByCode] = useState({}); // { "EECS 2311": {...details} }
  const [loadingByCode, setLoadingByCode] = useState({}); // { "EECS 2311": true }

  useEffect(() => {
    const t = setTimeout(async () => {
      try {
        // term-filtered search (backend can ignore season/year if not implemented)
        const data = await searchCourses(q, termSeason, termYear);
        setResults(Array.isArray(data) ? data : []);
      } catch (e) {
        console.error("searchCourses failed:", e);
        setResults([]);
      }
    }, 250);

    return () => clearTimeout(t);
  }, [q, termSeason, termYear]);

  async function toggleInfo(courseCode) {
    const next = !expanded[courseCode];
    setExpanded((prev) => ({ ...prev, [courseCode]: next }));

    // Fetch details only the first time we open it (cache)
    if (next && !detailsByCode[courseCode]) {
      setLoadingByCode((prev) => ({ ...prev, [courseCode]: true }));
      try {
        const d = await getCourseDetails(courseCode, termSeason, termYear);
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
    <div className="card">
      <h2>Course Search</h2>

      {/* Term filter */}
      <div style={{ display: "flex", gap: 8, marginBottom: 10, alignItems: "end" }}>
        <label style={{ flex: 1 }}>
          Season
          <select value={termSeason} onChange={(e) => setTermSeason(e.target.value)}>
            <option value="FALL">FALL</option>
            <option value="WINTER">WINTER</option>
          </select>
        </label>

        <label style={{ width: 140 }}>
          Year
          <select value={termYear} onChange={(e) => setTermYear(parseInt(e.target.value, 10))}>
            <option value={2026}>2026</option>
            <option value={2027}>2027</option>
          </select>
        </label>
      </div>

      <input
        value={q}
        onChange={(e) => setQ(e.target.value)}
        placeholder="Search e.g. EECS, MATH..."
      />

      <div className="list">
        {results.slice(0, 15).map((c) => {
          const code = c.courseCode;
          const selectionKey = `${termSeason}-${termYear}-${code}`;
          const isOpen = !!expanded[code];
          const details = detailsByCode[code];
          const loading = !!loadingByCode[code];

          return (
            <div key={code} className="row" style={{ display: "block" }}>
              <div style={{ display: "flex", justifyContent: "space-between", gap: 8 }}>
                <div>
                  <b>{code}</b> {c.title}
                </div>

                <div style={{ display: "flex", gap: 8 }}>
                  <button className="btn" onClick={() => toggleInfo(code)}>
                    {isOpen ? "Hide Info" : "More Info"}
                  </button>

                  <button
                    className={`btn ${selectedSet.has(selectionKey) ? "danger" : ""}`}
                    onClick={() => onToggle(code)}   // onToggle already builds the key in Dashboard
                  >
                    {selectedSet.has(selectionKey) ? "Remove" : "Add"}
                  </button>
                </div>
              </div>

              {/* Expand details */}
              {isOpen && (
                <div className="card inner" style={{ marginTop: 10 }}>
                  <div className="muted" style={{ marginBottom: 8 }}>
                    Term: <b>{termSeason} {termYear}</b>
                  </div>

                  {loading && <div className="muted">Loading...</div>}

                  {!loading && details?.error && <div className="error">{details.error}</div>}

                  {!loading && details && !details.error && (
                    <>
                      <div style={{ marginBottom: 10 }}>
                        <b>Description:</b>
                        <div className="muted">{details.description || "No description."}</div>
                      </div>

                      <div>
                        <b>Sections:</b>
                        {Array.isArray(details.sections) && details.sections.length > 0 ? (
                          <div style={{ marginTop: 8, display: "grid", gap: 8 }}>
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