import React, { useEffect, useMemo, useState } from "react";
import TopBar from "../components/layout/TopBar.jsx";
import CourseSearch from "../components/dashboard/CourseSearch.jsx";
import SelectedCourses from "../components/dashboard/SelectedCourse.jsx";
import AllCourses from "../components/dashboard/AllCourses.jsx";
import { listCourses } from "../api/CourseApi";
import { listTerms } from "../api/TermsApi";

export default function Dashboard() {
  const [allCourses, setAllCourses] = useState([]);
  const [selected, setSelected] = useState([]); // ["FALL-2026-EECS 2311", ...]
  const [schedule, setSchedule] = useState(null);

  const keyOf = (season, year, courseCode) => `${season}-${year}-${courseCode}`;
  const courseFromKey = (key) => key.split("-").slice(2).join("-");

  function toggleCourseForCurrentTerm(courseCode) {
    const key = keyOf(termSeason, termYear, courseCode);
    setSchedule(null);
    setSelected((prev) => (prev.includes(key) ? prev.filter((k) => k !== key) : [...prev, key]));
  }

  // term state (shared)
  const [terms, setTerms] = useState([]);
  const [termSeason, setTermSeason] = useState("FALL");
  const [termYear, setTermYear] = useState(2026);

  const selectedSet = useMemo(() => new Set(selected), [selected]);

  useEffect(() => {
    (async () => {
      try {
        const data = await listCourses();
        setAllCourses(Array.isArray(data) ? data : []);
      } catch (e) {
        console.error("Course load failed:", e.message);
      }
    })();
  }, []);

  useEffect(() => {
    (async () => {
      try {
        const data = await listTerms();
        const arr = Array.isArray(data) ? data : [];
        setTerms(arr);

        // pick first term as default if available
        if (arr.length > 0) {
          setTermSeason(arr[0].season);
          setTermYear(arr[0].year);
        }
      } catch (e) {
        console.warn("Term load failed (using defaults):", e.message);
      }
    })();
  }, []);

  function toggle(code) {
    setSchedule(null);
    setSelected((prev) =>
      prev.includes(code) ? prev.filter((c) => c !== code) : [...prev, code]
    );
  }

  return (
    <>
      <TopBar />

      <div className="grid">
        <CourseSearch
          selectedSet={new Set(selected)}   // selected now contains keys
          onToggle={toggleCourseForCurrentTerm}
          termSeason={termSeason}
          termYear={termYear}
          setTermSeason={setTermSeason}
          setTermYear={setTermYear}
        />

        <SelectedCourses
          selected={selected}
          onToggle={toggle}
          schedule={schedule}
          setSchedule={setSchedule}
          termSeason={termSeason}
          termYear={termYear}
          setTermSeason={setTermSeason}
          setTermYear={setTermYear}
        />

        <AllCourses allCourses={allCourses} selectedSet={selectedSet} onToggle={toggle} />
      </div>
    </>
  );
}