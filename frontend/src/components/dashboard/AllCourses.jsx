import React from "react";

export default function AllCourses({
  allCourses,
  selectedSet,
  onToggle,
}) {
  return (
    <div className="card">
      <h2>All Courses (Protected)</h2>

      <div className="list">
        {allCourses.slice(0, 30).map((c) => (
          <div key={c.courseCode} className="row">
            <div>
              <b>{c.courseCode}</b> {c.title}
            </div>

            <button
              className="btn"
              onClick={() => onToggle(c.courseCode)}
            >
              {selectedSet.has(c.courseCode) ? "Remove" : "Add"}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
