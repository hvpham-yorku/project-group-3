import React, { useEffect, useMemo, useState } from "react";
import TopBar from "../components/layout/TopBar.jsx";
import { buildSchedule } from "../api/ScheduleApi.js";

import CourseSearch from "../components/dashboard/CourseSearch.jsx";
import SelectedCourses from "../components/dashboard/SelectedCourse.jsx";
import ProgramChecklist from "../components/dashboard/ProgramChecklist.jsx";

export default function Dashboard({ theme, onToggleTheme }) {
  const [selectedTerm, setSelectedTerm] = useState("FALL 2026");
  const [selected, setSelected] = useState([]);
  const [schedule, setSchedule] = useState(null);
  const [scheduleMsg, setScheduleMsg] = useState("");

  const selectedSet = useMemo(() => new Set(selected), [selected]);

  const prefixOf = (term) => {
    const [seasonRaw, yearRaw] = term.trim().split(/\s+/);
    const season = (seasonRaw || "FALL").toUpperCase();
    const year = yearRaw || "2026";
    return `${season}-${year}-`;
  };

  const keyOf = (term, courseCode) => `${prefixOf(term)}${courseCode}`;

  function toggleCourse(courseCode) {
    setSchedule(null);
    const key = keyOf(selectedTerm, courseCode);
    setSelected((prev) =>
      prev.includes(key) ? prev.filter((k) => k !== key) : [...prev, key]
    );
  }

  function removeKey(key) {
    setSchedule(null);
    setSelected((prev) => prev.filter((k) => k !== key));
  }

  const prefix = useMemo(() => prefixOf(selectedTerm), [selectedTerm]);

  const selectedForTerm = useMemo(
    () => selected.filter((k) => k.startsWith(prefix)),
    [selected, prefix]
  );

  const courseCodesForTerm = useMemo(
    () => selectedForTerm.map((k) => k.slice(prefix.length)),
    [selectedForTerm, prefix]
  );

  async function onBuildSchedule() {
    setScheduleMsg("");
    setSchedule(null);
    try {
      const res = await buildSchedule(selectedTerm, courseCodesForTerm);
      setSchedule(res);
    } catch (e) {
      setScheduleMsg(e.message);
    }
  }

  const termLocked = selected.length > 0;

  useEffect(() => {
    document.body.classList.toggle("hasSchedule", Boolean(schedule));
    return () => document.body.classList.remove("hasSchedule");
  }, [schedule]);

  return (
    <>
      <TopBar theme={theme} onToggleTheme={onToggleTheme} />

      <div className="grid">
        <div className="searchPanel">
          <CourseSearch
            selectedSet={selectedSet}
            onToggle={toggleCourse}
            selectedTerm={selectedTerm}
            setSelectedTerm={setSelectedTerm}
            termLocked={termLocked}
            selectedCount={selectedForTerm.length}
            onBuildSchedule={onBuildSchedule}
          />
        </div>

        <div className="checklistPanel">
          <ProgramChecklist />
        </div>

        <div className="schedulePanel">
          <SelectedCourses
            selected={selected}
            removeKey={removeKey}
            schedule={schedule}
            msg={scheduleMsg}
            selectedTerm={selectedTerm}
          />
        </div>
      </div>
    </>
  );
}
