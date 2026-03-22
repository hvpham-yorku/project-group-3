import React, { useEffect, useMemo, useRef, useState } from "react";
import { listFaculties, listPrograms } from "../../api/ProgramApi.js";

export default function RegisterCard({ onRegister }) {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [showPass, setShowPass] = useState(false);

  const [faculties, setFaculties] = useState([]);
  const [facultyId, setFacultyId] = useState("");
  const [programs, setPrograms] = useState([]);
  const [programId, setProgramId] = useState(null);
  const [programQuery, setProgramQuery] = useState("");
  const [programOpen, setProgramOpen] = useState(false);
  const [msg, setMsg] = useState("");

  const boxRef = useRef(null);

  useEffect(() => {
    (async () => {
      try {
        const data = await listFaculties();
        setFaculties(Array.isArray(data) ? data : []);
      } catch (error) {
        setMsg(error.message || "Failed to load faculties");
      }
    })();
  }, []);

  useEffect(() => {
    if (!facultyId) {
      setPrograms([]);
      setProgramId(null);
      setProgramQuery("");
      return;
    }

    (async () => {
      try {
        const data = await listPrograms(facultyId);
        setPrograms(Array.isArray(data) ? data : []);
        setProgramId(null);
        setProgramQuery("");
      } catch (error) {
        setMsg(error.message || "Failed to load programs");
      }
    })();
  }, [facultyId]);

  useEffect(() => {
    function onDocClick(event) {
      if (!boxRef.current) return;
      if (!boxRef.current.contains(event.target)) setProgramOpen(false);
    }

    document.addEventListener("mousedown", onDocClick);
    return () => document.removeEventListener("mousedown", onDocClick);
  }, []);

  const filteredPrograms = useMemo(() => {
    const query = programQuery.trim().toLowerCase();
    if (!query) return programs;

    return programs.filter((program) => {
      const label = `${program.name || ""} ${program.degree || ""}`.toLowerCase();
      return label.includes(query);
    });
  }, [programQuery, programs]);

  function pickProgram(program) {
    setProgramId(program.id);
    setProgramQuery(`${program.degree ? `${program.degree} ` : ""}${program.name}`);
    setProgramOpen(false);
  }

  async function submit(event) {
    event.preventDefault();
    setMsg("");

    const fn = firstName.trim();
    const ln = lastName.trim();
    const em = email.trim();

    if (!fn) return setMsg("Please enter your first name.");
    if (!ln) return setMsg("Please enter your last name.");
    if (!em) return setMsg("Please enter your email.");
    if (!facultyId) return setMsg("Please choose a faculty.");
    if (!programId) return setMsg("Please choose a program.");
    if (!password) return setMsg("Please enter a password.");
    if (password !== confirmPassword) return setMsg("Passwords do not match.");

    try {
      await onRegister({
        firstName: fn,
        lastName: ln,
        email: em,
        programId,
        password,
        confirmPassword,
      });
    } catch (error) {
      setMsg(error.message || "Registration failed");
    }
  }

  return (
    <div className="card authCard">
      <div className="authHeader">
        <div className="authLogo" aria-hidden="true">
          YU
        </div>

        <div>
          <h2 className="authTitle">Create your account</h2>
          <div className="muted authSubtitle">
            Choose your program to unlock your checklist.
          </div>
        </div>
      </div>

      <form onSubmit={submit} className="form authForm">
        <div className="authRow2">
          <label>
            First name
            <input
              value={firstName}
              onChange={(event) => setFirstName(event.target.value)}
              autoComplete="given-name"
              placeholder="e.g., Jostin"
            />
          </label>

          <label>
            Last name
            <input
              value={lastName}
              onChange={(event) => setLastName(event.target.value)}
              autoComplete="family-name"
              placeholder="e.g., Martinez"
            />
          </label>
        </div>

        <label>
          Email
          <input
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            autoComplete="username"
            placeholder="you@email.com"
          />
        </label>

        <label>
          Faculty
          <select value={facultyId} onChange={(event) => setFacultyId(event.target.value)}>
            <option value="">Select a faculty...</option>
            {faculties.map((faculty) => (
              <option key={faculty.id} value={faculty.id}>
                {faculty.name}
              </option>
            ))}
          </select>
        </label>

        <label>
          Program
          <div className="combo" ref={boxRef}>
            <div className="comboRow">
              <input
                value={programQuery}
                onChange={(event) => {
                  setProgramQuery(event.target.value);
                  setProgramOpen(true);
                  setProgramId(null);
                }}
                onFocus={() => setProgramOpen(true)}
                placeholder={facultyId ? "Search or pick a program..." : "Choose faculty first"}
                disabled={!facultyId}
              />
              <button
                type="button"
                className="btn comboBtn"
                onClick={() => facultyId && setProgramOpen((value) => !value)}
                disabled={!facultyId}
                aria-label="Toggle program list"
              >
                v
              </button>
            </div>

            {programOpen && facultyId && (
              <div className="comboList">
                {filteredPrograms.length === 0 ? (
                  <div className="comboItem muted">No results</div>
                ) : (
                  filteredPrograms.map((program) => (
                    <button
                      type="button"
                      key={program.id}
                      className="comboItem"
                      onClick={() => pickProgram(program)}
                    >
                      <div className="comboMain">{program.name}</div>
                      <div className="comboSub muted">{program.degree || ""}</div>
                    </button>
                  ))
                )}
              </div>
            )}
          </div>
        </label>

        <div className="authRow2">
          <label>
            Password
            <div className="authPassWrap">
              <input
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                type={showPass ? "text" : "password"}
                autoComplete="new-password"
                placeholder="Create a password"
              />
              <button
                type="button"
                className="btn authPassBtn"
                onClick={() => setShowPass((value) => !value)}
              >
                {showPass ? "Hide" : "Show"}
              </button>
            </div>
          </label>

          <label>
            Confirm password
            <input
              value={confirmPassword}
              onChange={(event) => setConfirmPassword(event.target.value)}
              type={showPass ? "text" : "password"}
              autoComplete="new-password"
              placeholder="Repeat password"
            />
          </label>
        </div>

        {msg && <div className="error">{msg}</div>}

        <button className="btn primary authSubmit" type="submit">
          Create Account
        </button>
      </form>
    </div>
  );
}
