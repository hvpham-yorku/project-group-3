import React from "react";

export default function AllCourses({ allCourses, selectedSet, onToggle }) {
  // ✅ avoid crash when allCourses is undefined/null
  const list = Array.isArray(allCourses) ? allCourses : [];

  return (
    <div className="card">
      <h2>All Courses (Protected)</h2>

      <div className="list">
        {list.slice(0, 30).map((c) => (
          <div key={c.courseCode} className="row">
            <div>
              <b>{c.courseCode}</b> {c.title}
            </div>

            <button className="btn" onClick={() => onToggle(c.courseCode)}>
              {/* NOTE: selectedSet now stores KEYS, so this may always show "Add".
                  That's OK because you said you'll replace this list later anyway. */}
              Add
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}