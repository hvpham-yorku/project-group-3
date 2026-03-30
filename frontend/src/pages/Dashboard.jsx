/**
 * Main authenticated dashboard for schedule planning.
 *
 * This page coordinates selected-course persistence, course search, checklist
 * display, and schedule generation for the currently chosen term.
 */
import React, { useEffect, useMemo, useState } from "react";
import TopBar from "../components/layout/TopBar.jsx";
import { buildSchedule } from "../api/ScheduleApi.js";
import {
  addSelectedCourse,
  listSelectedCourses,
  removeSelectedCourse,
} from "../api/SelectedCoursesApi.js";
import {
  buildSelectedCourseKey,
  buildSelectedCourseKeyPrefix,
  parseSelectedCourseKey,
} from "../utils/selectedCourseKey.js";

import CourseSearch from "../components/dashboard/CourseSearch.jsx";
import SelectedCourses from "../components/dashboard/SelectedCourse.jsx";
import ProgramChecklist from "../components/dashboard/ProgramChecklist.jsx";

/**
 * Composes the core schedule-building workflow after sign-in.
 */
export default function Dashboard({ theme, onToggleTheme, onNavigate }) {
  const [selectedTerm, setSelectedTerm] = useState("FALL 2026");
  const [selected, setSelected] = useState([]);
  const [schedule, setSchedule] = useState(null);
  const [scheduleMsg, setScheduleMsg] = useState("");
  const [saveError, setSaveError] = useState("");
  const [saving, setSaving] = useState(false);

  const selectedSet = useMemo(() => new Set(selected), [selected]);

  // This lookup lets the search UI explain when a course is already saved under a different term.
  const savedCourseTermByCode = useMemo(() => {
    return Object.fromEntries(
      selected.map((key) => {
        const { term, courseCode } = parseSelectedCourseKey(key);
        return [courseCode, term];
      })
    );
  }, [selected]);

  useEffect(() => {
    let ignore = false;

    // Load persisted selected courses once so dashboard state matches the backend immediately after refresh.
    async function loadSelectedCourses() {
      try {
        const saved = await listSelectedCourses();
        if (ignore) {
          return;
        }
        setSelected(
          (Array.isArray(saved) ? saved : []).map((item) =>
            buildSelectedCourseKey(item.term, item.courseCode)
          )
        );
      } catch (e) {
        if (!ignore) {
          setSaveError(e.message || "Failed to load saved courses");
        }
      }
    }

    loadSelectedCourses();
    return () => {
      ignore = true;
    };
  }, []);

  /**
   * Adds or removes a course for the currently selected term and keeps the
   * local selection cache synchronized with the backend.
   */
  async function toggleCourse(courseCode) {
    setSchedule(null);
    setScheduleMsg("");
    setSaveError("");
    const key = buildSelectedCourseKey(selectedTerm, courseCode);
    const alreadySelected = selected.includes(key);

    setSaving(true);
    try {
      if (alreadySelected) {
        await removeSelectedCourse(selectedTerm, courseCode);
        setSelected((prev) => prev.filter((k) => k !== key));
      } else {
        await addSelectedCourse(selectedTerm, courseCode);
        setSelected((prev) => (prev.includes(key) ? prev : [...prev, key]));
      }
    } catch (e) {
      setSaveError(e.message || "Failed to update saved courses");
    } finally {
      setSaving(false);
    }
  }

  /**
   * Removes a saved course selection identified by the flattened UI key.
   */
  async function removeKey(key) {
    setSchedule(null);
    setScheduleMsg("");
    setSaveError("");
    const { term, courseCode } = parseSelectedCourseKey(key);

    setSaving(true);
    try {
      await removeSelectedCourse(term, courseCode);
      setSelected((prev) => prev.filter((current) => current !== key));
    } catch (e) {
      setSaveError(e.message || "Failed to update saved courses");
    } finally {
      setSaving(false);
    }
  }

  const prefix = useMemo(() => buildSelectedCourseKeyPrefix(selectedTerm), [selectedTerm]);

  // Derived selection subsets keep the child components focused on the active term only.
  const selectedForTerm = useMemo(
    () => selected.filter((k) => k.startsWith(prefix)),
    [selected, prefix]
  );

  const courseCodesForTerm = useMemo(
    () => selectedForTerm.map((k) => k.slice(prefix.length)),
    [selectedForTerm, prefix]
  );

  /**
   * Requests a built schedule for the currently active term selection.
   */
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

  useEffect(() => {
    // Body-level styling widens the layout once a schedule grid is present.
    document.body.classList.toggle("hasSchedule", Boolean(schedule));
    return () => document.body.classList.remove("hasSchedule");
  }, [schedule]);

  useEffect(() => {
    // Changing term invalidates the previously built schedule output.
    setSchedule(null);
    setScheduleMsg("");
  }, [selectedTerm]);

  return (
    <>
      <TopBar theme={theme} onToggleTheme={onToggleTheme} activeNav="schedule" onNavigate={onNavigate} />

      <div className="grid">
        <div className="searchPanel">
          <CourseSearch
            selectedSet={selectedSet}
            savedCourseTermByCode={savedCourseTermByCode}
            onToggle={toggleCourse}
            selectedTerm={selectedTerm}
            setSelectedTerm={setSelectedTerm}
            selectedCount={selectedForTerm.length}
            onBuildSchedule={onBuildSchedule}
            saving={saving}
            saveError={saveError}
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
