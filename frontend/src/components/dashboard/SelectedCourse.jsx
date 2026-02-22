import React, { useState } from "react";
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
    <div className="card">
      <h2>Selected Courses</h2>

      <label>
        Term
        <input
          value={term}
          onChange={(e) => setTerm(e.target.value)}
        />
      </label>

      <div className="pillRow">
        {selected.length === 0 && (
          <span className="muted">No courses selected</span>
        )}

        {selected.map((code) => (
          <button
            key={code}
            className="pill"
            onClick={() => onToggle(code)}
          >
            {code} ✕
          </button>
        ))}
      </div>

      <button
        className="btn primary"
        disabled={selected.length === 0}
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
