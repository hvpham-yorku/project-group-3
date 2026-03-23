import React, { useMemo } from "react";
import ScheduleGrid from "../ScheduleGrid.jsx";
import {
  detectScheduleConflicts,
  formatDayLabel,
  formatRange,
} from "../../utils/scheduleConflicts.js";

export default function SelectedCourses({
  selected,
  removeKey,
  schedule,
  selectedTerm,
  msg,
}) {
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

  const conflicts = useMemo(
    () => (schedule ? detectScheduleConflicts(schedule.chosenSections) : { hasConflicts: false, segments: [] }),
    [schedule]
  );
  const cardClassName = schedule
    ? "card selectedCoursesCard"
    : "card selectedCoursesCard selectedCoursesCardTall";

  return (
    <div className={cardClassName}>
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

      {msg && <div className="error">{msg}</div>}

      {schedule && (
        <div className="card inner">
          <h3>Schedule Result</h3>
          <ScheduleGrid chosenSections={schedule.chosenSections} termLabel={schedule.term} />

          {conflicts.hasConflicts && (
            <div className="conflictPanel">
              <h4>Schedule Conflicts</h4>
              <p className="conflictIntro">
                There is at least one block of time where more than one course is scheduled
                to meet simultaneously. Each overlap is listed below.
              </p>

              <ul className="conflictList">
                {conflicts.segments.map((segment) => (
                  <li key={`${segment.day}-${segment.start}-${segment.end}-${segment.signature}`}>
                    <div className="conflictRange">
                      {formatDayLabel(segment.day, true)}, {formatRange(segment.start, segment.end)}
                    </div>

                    <ul className="conflictCourses">
                      {segment.occurrences.map((item) => (
                        <li key={item.eventId}>
                          {item.courseCode} | Section {item.sectionId}
                          {item.location ? ` | ${item.location}` : ""}
                          {item.startTime && item.endTime
                            ? ` | ${String(item.startTime).slice(0, 5)}-${String(item.endTime).slice(0, 5)}`
                            : ""}
                        </li>
                      ))}
                    </ul>
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
