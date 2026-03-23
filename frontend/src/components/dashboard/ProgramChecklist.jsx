import React, { useEffect, useMemo, useState } from "react";
import { getMyChecklist } from "../../api/ChecklistApi.js";

export default function ProgramChecklist() {
  const [data, setData] = useState(null);
  const [openYears, setOpenYears] = useState(() => new Set([1]));
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

  const years = useMemo(() => data?.years || [], [data]);

  function toggleYear(year) {
    setOpenYears((prev) => {
      const next = new Set(prev);
      if (next.has(year)) next.delete(year);
      else next.add(year);
      return next;
    });
  }

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
          {years.map((y) => {
            const isOpen = openYears.has(y.year);
            return (
              <div className="card inner" key={y.year}>
                <div className="row">
                  <div>
                    <b>Year {y.year}</b>
                    <div className="muted" style={{ fontSize: 12 }}>
                      {y.groups?.reduce((n, g) => n + (g.courses?.length || 0), 0)} courses
                    </div>
                  </div>

                  <button className="btn" onClick={() => toggleYear(y.year)}>
                    {isOpen ? "Collapse" : "Expand"}
                  </button>
                </div>

                {isOpen && (
                  <div className="list checklistExpanded">
                    {y.groups?.map((g, idx) => (
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
