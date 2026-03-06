import React, { useMemo, useState } from "react";
import { buildSchedule } from "../../api/ScheduleApi.js";
import ScheduleGrid from "../ScheduleGrid.jsx";

export default function SelectedCourses({
  selected,
  removeKey,
  schedule,
  setSchedule,
  selectedTerm,
}) {
  const [msg, setMsg] = useState("");

  const prefix = useMemo(() => {
    const [seasonRaw, yearRaw] = selectedTerm.trim().split(/\s+/);
    const season = (seasonRaw || "FALL").toUpperCase();
    const year = yearRaw || "2026";
    return `${season}-${year}-`;
  }, [selectedTerm]);

  const selectedForTerm = useMemo(
    () => selected.filter((k) => k.startsWith(prefix)),
    [selected, prefix]
  );

  const courseCodesForTerm = useMemo(
    () => selectedForTerm.map((k) => k.slice(prefix.length)),
    [selectedForTerm, prefix]
  );

  async function onBuild() {
    setMsg("");
    setSchedule(null);
    try {
      const res = await buildSchedule(selectedTerm, courseCodesForTerm);
      setSchedule(res);
    } catch (e) {
      setMsg(e.message);
    }
  }

  return (
    <div className="card">
      <h2>Selected Courses</h2>

      <div className="muted">
        Term: <b>{selectedTerm}</b>
      </div>

      <div className="pillRow">
        {selectedForTerm.length === 0 && <span className="muted">No courses selected</span>}

        {selectedForTerm.map((key) => {
          const courseCode = key.slice(prefix.length);
          return (
            <button key={key} className="pill" onClick={() => removeKey(key)}>
              {courseCode} ✕
            </button>
          );
        })}
      </div>

      <button className="btn primary" disabled={selectedForTerm.length === 0} onClick={onBuild}>
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