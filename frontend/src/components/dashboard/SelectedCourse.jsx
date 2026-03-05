import React, { useMemo, useState } from "react";
import { buildSchedule } from "../../api/ScheduleApi.js";
import ScheduleGrid from "../ScheduleGrid.jsx";

export default function SelectedCourses({
  selected,
  onToggle,
  schedule,
  setSchedule,
}) {
  const [term, setTerm] = useState("FALL 2026");
  const [msg, setMsg] = useState("");

  // term -> prefix "FALL-2026-"
  const prefix = useMemo(() => {
    const parts = term.trim().split(/\s+/); // ["FALL","2026"]
    const season = (parts[0] || "FALL").toUpperCase();
    const year = parts[1] || "2026";
    return `${season}-${year}-`;
  }, [term]);

  // selected keys for this term only
  const selectedForTerm = useMemo(
    () => selected.filter((k) => k.startsWith(prefix)),
    [selected, prefix]
  );

  // course codes extracted from keys
  const courseCodesForTerm = useMemo(
    () => selectedForTerm.map((k) => k.slice(prefix.length)),
    [selectedForTerm, prefix]
  );

  async function onBuild() {
    setMsg("");
    setSchedule(null);

    try {
      const res = await buildSchedule(term, courseCodesForTerm);
      setSchedule(res);
    } catch (e) {
      setMsg(e.message);
    }
  }

  return (
    <div className="card">
      <h2>Selected Courses</h2>

      <label>
        Term
        <input value={term} onChange={(e) => setTerm(e.target.value)} />
      </label>

      <div className="pillRow">
        {selectedForTerm.length === 0 && (
          <span className="muted">No courses selected for {term}</span>
        )}

        {selectedForTerm.map((key) => {
          const courseCode = key.slice(prefix.length);
          return (
            <button key={key} className="pill" onClick={() => onToggle(key)}>
              {courseCode} ✕
            </button>
          );
        })}
      </div>

      <button
        className="btn primary"
        disabled={selectedForTerm.length === 0}
        onClick={onBuild}
      >
        Build Schedule
      </button>

      {msg && <div className="error">{msg}</div>}

      {schedule && (
        <div className="card inner">
          <h3>Schedule Result</h3>
          <div className="muted">Term: {schedule.term}</div>
          <ScheduleGrid chosenSections={schedule.chosenSections} />
        </div>
      )}
    </div>
  );
}