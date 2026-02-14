import React, { useEffect, useMemo, useState } from "react";
import { buildSchedule, listCourses, searchCourses } from "./api.js";
import ScheduleGrid from "./ScheduleGrid.jsx";

/**
 * Dashboard.jsx
 * Main authenticated page:
 * - Public course search (GET /api/search/courses?q=...)
 * - Protected full course list (GET /api/courses)
 * - Schedule builder (POST /api/schedule/build)
 *
 * UI note:
 * - When a schedule exists we add the "hasSchedule" class to <body>
 *   to expand the layout width (see index.css).
 */
export default function Dashboard() {
  // Protected course list (requires JWT)
  const [allCourses, setAllCourses] = useState([]);

  // Public search state
  const [q, setQ] = useState("");
  const [searchResults, setSearchResults] = useState([]);

  // Selected course codes (chips)
  const [selected, setSelected] = useState([]);

  // Term is typed by the user (backend expects a term string)
  const [term, setTerm] = useState("FALL 2026");

  // Message area for errors/info
  const [msg, setMsg] = useState("");

  // Schedule build result: { term, chosenSections: [...] }
  const [schedule, setSchedule] = useState(null);

  // Expand the container width after schedule is built (better UX for the grid)
  useEffect(() => {
    document.body.classList.toggle("hasSchedule", Boolean(schedule));
    return () => document.body.classList.remove("hasSchedule");
  }, [schedule]);

  // Quick membership checks: O(1) add/remove button label changes
  const selectedSet = useMemo(() => new Set(selected), [selected]);

  // Load protected course list once
  useEffect(() => {
    (async () => {
      try {
        const data = await listCourses();
        setAllCourses(Array.isArray(data) ? data : []);
      } catch (e) {
        setMsg(`Courses load failed: ${e.message}`);
      }
    })();
  }, []);

  // Debounced course search (public endpoint)
  useEffect(() => {
    const t = setTimeout(async () => {
      try {
        const data = await searchCourses(q);
        setSearchResults(Array.isArray(data) ? data : []);
      } catch {
        // ignore search errors (usually happens when typing fast)
      }
    }, 250);

    return () => clearTimeout(t);
  }, [q]);

  /**
   * Toggle a course selection and clear schedule result.
   * (If user changes selection, schedule must be rebuilt.)
   */
  function toggle(code) {
    setSchedule(null);
    setMsg("");
    setSelected((prev) =>
      prev.includes(code) ? prev.filter((c) => c !== code) : [...prev, code]
    );
  }

  /**
   * Request a non-conflicting schedule from the backend.
   */
  async function onBuild() {
    setMsg("");
    setSchedule(null);

    try {
      const res = await buildSchedule(term, selected);
      setSchedule(res);
    } catch (e) {
      setMsg(e.message);
    }
  }

  return (
    <div className="grid">
      {/* LEFT PANEL: Public course search */}
      <div className="card">
        <h2>Course Search</h2>
        <p className="muted">
          Public search: <code>GET /api/search/courses?q=</code>
        </p>

        <input
          value={q}
          onChange={(e) => setQ(e.target.value)}
          placeholder="Search e.g. EECS, MATH..."
        />

        <div className="list">
          {(searchResults || []).slice(0, 15).map((c) => (
            <CourseRow
              key={c.courseCode}
              course={c}
              isSelected={selectedSet.has(c.courseCode)}
              onToggle={toggle}
            />
          ))}
        </div>
      </div>

      {/* RIGHT PANEL: Selected courses + schedule build */}
      <div className="card">
        <h2>Selected Courses</h2>
        <p className="muted">
          Build schedule (auth): <code>POST /api/schedule/build</code>
        </p>

        <label>
          Term
          <input value={term} onChange={(e) => setTerm(e.target.value)} placeholder="FALL 2026" />
        </label>

        {/* Selected course chips */}
        <div className="pillRow">
          {selected.length === 0 ? <span className="muted">No courses selected</span> : null}

          {selected.map((code) => (
            <button
              key={code}
              className="pill"
              onClick={() => toggle(code)}
              title="Remove"
            >
              {code} ✕
            </button>
          ))}
        </div>

        <button className="btn primary" disabled={selected.length === 0} onClick={onBuild}>
          Build Schedule
        </button>

        {msg ? <div className="error">{msg}</div> : null}

        {/* Schedule result table */}
        {schedule ? (
          <div className="card inner">
            <h3>Schedule Result</h3>
            <div className="muted">Term: {schedule.term}</div>

            <ScheduleGrid chosenSections={schedule.chosenSections} />

            {/* NOTE: Removed raw JSON output for a cleaner UI.
               If you want it back for debugging, add a "Show details" toggle. */}
          </div>
        ) : null}
      </div>

      {/* BOTTOM PANEL: Protected full course list */}
      <div className="card">
        <h2>All Courses (Protected)</h2>
        <p className="muted">
          Requires token: <code>GET /api/courses</code>
        </p>

        <div className="list">
          {(allCourses || []).slice(0, 30).map((c) => (
            <div key={c.courseCode} className="row">
              <div>
                <b>{c.courseCode}</b> <span className="muted">{c.title || ""}</span>
              </div>

              <button className="btn" onClick={() => toggle(c.courseCode)}>
                {selectedSet.has(c.courseCode) ? "Remove" : "Add"}
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

/**
 * Reusable row for search results.
 */
function CourseRow({ course, isSelected, onToggle }) {
  return (
    <div className="row">
      <div>
        <b>{course.courseCode}</b> <span className="muted">{course.title || ""}</span>
      </div>

      <button className={`btn ${isSelected ? "danger" : ""}`} onClick={() => onToggle(course.courseCode)}>
        {isSelected ? "Remove" : "Add"}
      </button>
    </div>
  );
}
