/**
 * Legacy file (likely unused in Vite build).
 * The app entrypoint imports App.jsx from main.jsx.
 * Keep this only if your team still uses it for reference.
 */

import React, { useEffect, useMemo, useState } from "react";
import "./App.css";

const API_BASE = "http://localhost:8080";

function normalizeCode(code) {
  return (code || "").trim();
}

function prereqStringToArray(text) {
  const t = (text || "").trim();
  if (!t) return [];
  // split by comma, trim each, remove empties
  return t.split(",").map(s => s.trim()).filter(Boolean);
}

export default function App() {
  const [courses, setCourses] = useState([]);
  const [query, setQuery] = useState("");
  const [loading, setLoading] = useState(false);

  const [form, setForm] = useState({
    code: "",
    title: "",
    credits: "3",
    prerequisitesText: "",
  });

  const [toast, setToast] = useState(null); // { type: "ok"|"err", msg: string }

  function showToast(type, msg) {
    setToast({ type, msg });
    window.clearTimeout(showToast._t);
    showToast._t = window.setTimeout(() => setToast(null), 3500);
  }

  async function loadCourses() {
    setLoading(true);
    try {
      const res = await fetch(`${API_BASE}/api/courses`);
      if (!res.ok) throw new Error(`GET failed (${res.status})`);
      const data = await res.json();
      setCourses(Array.isArray(data) ? data : []);
    } catch (e) {
      showToast("err", `Could not load courses: ${e.message}`);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadCourses();
  }, []);

  const filtered = useMemo(() => {
    const q = query.trim().toLowerCase();
    if (!q) return courses;
    return courses.filter(c => {
      const code = (c.code || "").toLowerCase();
      const title = (c.title || "").toLowerCase();
      return code.includes(q) || title.includes(q);
    });
  }, [courses, query]);

  async function handleCreate(e) {
    e.preventDefault();

    const payload = {
      code: normalizeCode(form.code),
      title: form.title.trim(),
      credits: Number(form.credits),
      prerequisites: prereqStringToArray(form.prerequisitesText),
    };

    if (!payload.code || !payload.title) {
      showToast("err", "Missing required fields: code, title");
      return;
    }
    if (!Number.isFinite(payload.credits) || payload.credits <= 0) {
      showToast("err", "Credits must be > 0");
      return;
    }

    try {
      const res = await fetch(`${API_BASE}/api/courses`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      // Your backend returns 409 with a text body for duplicates
      if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg || `POST failed (${res.status})`);
      }

      const created = await res.json();
      showToast("ok", `Created: ${created.code}`);
      setForm({ code: "", title: "", credits: "3", prerequisitesText: "" });
      await loadCourses();
    } catch (e) {
      showToast("err", e.message);
    }
  }

  return (
    <div className="page">
      <header className="topbar">
        <div className="brand">
          <div className="logo">YU</div>
          <div>
            <div className="title">YU Path Builder</div>
            <div className="subtitle">Courses Explorer (Backend: Spring Boot)</div>
          </div>
        </div>

        <button className="btn" onClick={loadCourses} disabled={loading}>
          {loading ? "Loading..." : "Refresh"}
        </button>
      </header>

      {toast && (
        <div className={`toast ${toast.type === "ok" ? "ok" : "err"}`}>
          {toast.msg}
        </div>
      )}

      <main className="grid">
        <section className="card">
          <div className="cardHeader">
            <h2>Courses</h2>
            <input
              className="search"
              placeholder="Search by code or title..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
            />
          </div>

          <div className="tableWrap">
            <table className="table">
              <thead>
                <tr>
                  <th style={{ width: 120 }}>Code</th>
                  <th>Title</th>
                  <th style={{ width: 80 }}>Credits</th>
                  <th style={{ width: 220 }}>Prereqs</th>
                </tr>
              </thead>
              <tbody>
                {filtered.map((c) => (
                  <tr key={c.code}>
                    <td className="mono">{c.code}</td>
                    <td>{c.title}</td>
                    <td>{c.credits}</td>
                    <td className="prereq">
                      {(c.prerequisites || []).length
                        ? (c.prerequisites || []).join(", ")
                        : "—"}
                    </td>
                  </tr>
                ))}
                {!filtered.length && (
                  <tr>
                    <td colSpan="4" className="empty">
                      No courses found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </section>

        <section className="card">
          <h2>Add a Course</h2>
          <form className="form" onSubmit={handleCreate}>
            <label>
              Code
              <input
                value={form.code}
                onChange={(e) => setForm({ ...form, code: e.target.value })}
                placeholder="EECS 3311"
              />
            </label>

            <label>
              Title
              <input
                value={form.title}
                onChange={(e) => setForm({ ...form, title: e.target.value })}
                placeholder="Software Design"
              />
            </label>

            <label>
              Credits
              <input
                value={form.credits}
                onChange={(e) => setForm({ ...form, credits: e.target.value })}
                placeholder="3"
              />
            </label>

            <label>
              Prerequisites (comma-separated)
              <input
                value={form.prerequisitesText}
                onChange={(e) =>
                  setForm({ ...form, prerequisitesText: e.target.value })
                }
                placeholder="EECS 2030, EECS 2011"
              />
            </label>

            <button className="btn primary" type="submit">
              Create
            </button>
            <div className="hint">
              Tip: prerequisites can be blank. Duplicate codes will return 409.
            </div>
          </form>
        </section>
      </main>
    </div>
  );
}
