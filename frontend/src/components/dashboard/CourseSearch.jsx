import React, { useEffect, useState } from "react";
import { searchCourses } from "../../api/CourseApi.js";

export default function CourseSearch({ selectedSet, onToggle }) {
  const [q, setQ] = useState("");
  const [results, setResults] = useState([]);

  useEffect(() => {
    const t = setTimeout(async () => {
      try {
        const data = await searchCourses(q);
        setResults(Array.isArray(data) ? data : []);
      } catch {
        // ignore rapid typing errors
      }
    }, 250);

    return () => clearTimeout(t);
  }, [q]);

  return (
    <div className="card">
      <h2>Course Search</h2>

      <input
        value={q}
        onChange={(e) => setQ(e.target.value)}
        placeholder="Search e.g. EECS, MATH..."
      />

      <div className="list">
        {results.slice(0, 15).map((c) => (
          <div key={c.courseCode} className="row">
            <div>
              <b>{c.courseCode}</b> {c.title}
            </div>

            <button
              className={`btn ${selectedSet.has(c.courseCode) ? "danger" : ""}`}
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
