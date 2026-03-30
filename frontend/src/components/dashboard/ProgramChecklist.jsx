/**
 * Program checklist panel displayed on the dashboard.
 *
 * This component loads the authenticated user's checklist and renders it in a
 * collapsible structure grouped by academic year and elective sections.
 */
import React, { useEffect, useMemo, useState } from "react";
import { getMyChecklist } from "../../api/ChecklistApi.js";

/**
 * Renders the checklist view for the signed-in user's program.
 */
export default function ProgramChecklist() {
  const [data, setData] = useState(null);
  const [openYears, setOpenYears] = useState(() => new Set([1, "electives"]));
  const [checked, setChecked] = useState(() => new Set()); // local-only for now
  const [msg, setMsg] = useState("");

  useEffect(() => {
    (async () => {
      try {
        const res = await getMyChecklist();
        setData(res);
        setMsg("");
      } catch (e) {
        setMsg(e.message || "Failed to load checklist");
        setData(null);
      }
    })();
  }, []);

  // Elective-like items are collected into a shared section so the checklist stays easier to scan.
  const sections = useMemo(() => {
    const years = data?.years || [];
    const electiveGroupsMap = new Map();
    const regularSections = [];

    for (const y of years) {
      const keptGroups = [];

      for (const g of y.groups || []) {
        const courses = g.courses || [];
        const moved = courses.filter(
          (c) => g.reqType === "ELECTIVE" || String(c.courseCode || "").startsWith("PKIN ")
        );
        const kept = courses.filter(
          (c) => !(g.reqType === "ELECTIVE" || String(c.courseCode || "").startsWith("PKIN "))
        );

        if (kept.length > 0) {
          keptGroups.push({ ...g, courses: kept });
        }

        if (moved.length > 0) {
          const key = `${g.groupName || "Core"}||${g.reqType || "ELECTIVE"}`;
          if (!electiveGroupsMap.has(key)) {
            electiveGroupsMap.set(key, {
              groupName: g.groupName || "Core",
              reqType: g.reqType || "ELECTIVE",
              courses: [],
            });
          }
          electiveGroupsMap.get(key).courses.push(...moved);
        }
      }

      if (keptGroups.length > 0) {
        regularSections.push({
          key: y.year,
          label: `Year ${y.year}`,
          groups: keptGroups,
        });
      }
    }

    const electiveGroups = Array.from(electiveGroupsMap.values());
    if (electiveGroups.length > 0) {
      regularSections.push({
        key: "electives",
        label: "Electives",
        groups: electiveGroups,
      });
    }

    return regularSections;
  }, [data]);

  /**
   * Expands or collapses one checklist section.
   */
  function toggleYear(yearKey) {
    setOpenYears((prev) => {
      const next = new Set(prev);
      if (next.has(yearKey)) next.delete(yearKey);
      else next.add(yearKey);
      return next;
    });
  }

  /**
   * Toggles the local completion checkbox state for a checklist course.
   */
  function toggleCourse(courseId) {
    setChecked((prev) => {
      const next = new Set(prev);
      if (next.has(courseId)) next.delete(courseId);
      else next.add(courseId);
      return next;
    });
  }

  return (
    <div className="card dashboardPanel checklistCard">
      <h3>Program Checklist</h3>
      <div className="muted">
        Courses are grouped by year. (Checkbox state is local for now.)
      </div>

      {msg && <div className="error">{msg}</div>}

      {!msg && !data && <div className="muted" style={{ marginTop: 10 }}>Loading…</div>}

      {data && (
        <div className="list checklistList">
          {sections.map((section) => {
            const isOpen = openYears.has(section.key);
            return (
              <div className="card inner" key={String(section.key)}>
                <div className="row">
                  <div>
                    <b>{section.label}</b>
                    <div className="muted" style={{ fontSize: 12 }}>
                      {section.groups?.reduce((n, g) => n + (g.courses?.length || 0), 0)} courses
                    </div>
                  </div>

                  <button className="btn" onClick={() => toggleYear(section.key)}>
                    {isOpen ? "Collapse" : "Expand"}
                  </button>
                </div>

                {isOpen && (
                  <div className="list checklistExpanded">
                    {section.groups?.map((g, idx) => (
                      <div key={`${g.groupName}-${g.reqType}-${idx}`} className="card inner">
                        <div style={{ display: "flex", gap: 10, alignItems: "baseline" }}>
                          <b>{g.groupName || "Core"}</b>
                          <span className="muted" style={{ fontSize: 12 }}>
                            {g.reqType}
                          </span>
                        </div>

                        <div className="list">
                          {g.courses?.map((c) => (
                            <div className="row" key={c.id}>
                              <label style={{ display: "flex", gap: 10, alignItems: "center" }}>
                                <input
                                  type="checkbox"
                                  checked={checked.has(c.id)}
                                  onChange={() => toggleCourse(c.id)}
                                />
                                <div>
                                  <b>{c.courseCode}</b>
                                  <div className="muted" style={{ fontSize: 12 }}>
                                    {c.title}
                                  </div>
                                </div>
                              </label>
                            </div>
                          ))}
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}
