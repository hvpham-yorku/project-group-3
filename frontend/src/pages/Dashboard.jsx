import React, { useEffect, useMemo, useState } from "react";
import TopBar from "../components/layout/TopBar.jsx";
import CourseSearch from "../components/dashboard/CourseSearch.jsx";
import SelectedCourses from "../components/dashboard/SelectedCourse.jsx";
import AllCourses from "../components/dashboard/AllCourses.jsx";
import { searchCourses, listCourses } from "../api/CourseApi";
import { buildSchedule } from "../api/ScheduleApi";

export default function Dashboard() {
  const [allCourses, setAllCourses] = useState([]);
  const [selected, setSelected] = useState([]);
  const [schedule, setSchedule] = useState(null);

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

  function toggle(code) {
    setSchedule(null);
    setSelected((prev) =>
      prev.includes(code)
        ? prev.filter((c) => c !== code)
        : [...prev, code]
    );
  }

  return (
    <>
      <TopBar />

      <div className="grid">
        <CourseSearch
          selectedSet={selectedSet}
          onToggle={toggle}
        />

        <SelectedCourses
          selected={selected}
          onToggle={toggle}
          schedule={schedule}
          setSchedule={setSchedule}
        />

        <AllCourses
          allCourses={allCourses}
          selectedSet={selectedSet}
          onToggle={toggle}
        />
      </div>
    </>
  );
}
