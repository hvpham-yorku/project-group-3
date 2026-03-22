import React, { useEffect, useMemo, useRef, useState } from "react";
import TopBar from "../components/layout/TopBar.jsx";
import { getProfile, updateProfile, changePassword } from "../api/ProfileApi.js";
import { listFaculties, listPrograms } from "../api/ProgramApi.js";
import { useAuth } from "../context/AuthContext.jsx";

const MAX_IMAGE_SIZE_BYTES = 2 * 1024 * 1024;

function readFileAsDataUrl(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => resolve(String(reader.result || ""));
    reader.onerror = () => reject(new Error("Failed to read the selected image"));
    reader.readAsDataURL(file);
  });
}

export default function ProfilePage({ theme, onToggleTheme, onNavigate }) {
  const { syncProfile } = useAuth();
  const [profile, setProfile] = useState(null);
  const [faculties, setFaculties] = useState([]);
  const [programs, setPrograms] = useState([]);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [facultyId, setFacultyId] = useState("");
  const [programId, setProgramId] = useState("");
  const [programQuery, setProgramQuery] = useState("");
  const [programOpen, setProgramOpen] = useState(false);
  const [profileImageData, setProfileImageData] = useState("");
  const [removeProfileImage, setRemoveProfileImage] = useState(false);
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [loading, setLoading] = useState(true);
  const [profileMsg, setProfileMsg] = useState("");
  const [passwordMsg, setPasswordMsg] = useState("");
  const [error, setError] = useState("");
  const boxRef = useRef(null);

  useEffect(() => {
    (async () => {
      try {
        const [profileData, facultyData] = await Promise.all([
          getProfile(),
          listFaculties(),
        ]);

        setProfile(profileData);
        syncProfile(profileData);
        setFirstName(profileData.firstName || "");
        setLastName(profileData.lastName || "");
        setFacultyId(profileData.facultyId ? String(profileData.facultyId) : "");
        setProgramId(profileData.programId ? String(profileData.programId) : "");
        setProgramQuery(
          profileData.programName
            ? `${profileData.programDegree ? `${profileData.programDegree} ` : ""}${profileData.programName}`
            : ""
        );
        setProfileImageData(profileData.profileImageData || "");
        setFaculties(Array.isArray(facultyData) ? facultyData : []);
      } catch (e) {
        setError(e.message || "Failed to load your profile");
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  useEffect(() => {
    if (!facultyId) {
      setPrograms([]);
      return;
    }

    (async () => {
      try {
        const data = await listPrograms(facultyId);
        setPrograms(Array.isArray(data) ? data : []);
      } catch (e) {
        setError(e.message || "Failed to load programs");
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
    setProgramId(String(program.id));
    setProgramQuery(`${program.degree ? `${program.degree} ` : ""}${program.name}`);
    setProgramOpen(false);
  }

  async function handlePhotoChange(event) {
    setError("");
    const file = event.target.files?.[0];
    if (!file) return;
    if (!file.type.startsWith("image/")) {
      setError("Please choose an image file for your profile photo.");
      return;
    }
    if (file.size > MAX_IMAGE_SIZE_BYTES) {
      setError("Please choose an image smaller than 2 MB.");
      return;
    }

    try {
      const dataUrl = await readFileAsDataUrl(file);
      setProfileImageData(dataUrl);
      setRemoveProfileImage(false);
    } catch (e) {
      setError(e.message || "Failed to read the selected image");
    } finally {
      event.target.value = "";
    }
  }

  async function submitProfile(event) {
    event.preventDefault();
    setProfileMsg("");
    setError("");

    if (!firstName.trim() || !lastName.trim()) {
      setError("Please provide your first and last name.");
      return;
    }
    if (!programId) {
      setError("Please choose a program.");
      return;
    }

    try {
      const updated = await updateProfile({
        firstName: firstName.trim(),
        lastName: lastName.trim(),
        programId: Number(programId),
        profileImageData: removeProfileImage ? "" : profileImageData,
        removeProfileImage,
      });
      setProfile(updated);
      syncProfile(updated);
      setFirstName(updated.firstName || "");
      setLastName(updated.lastName || "");
      setFacultyId(updated.facultyId ? String(updated.facultyId) : facultyId);
      setProgramId(updated.programId ? String(updated.programId) : "");
      setProgramQuery(
        updated.programName
          ? `${updated.programDegree ? `${updated.programDegree} ` : ""}${updated.programName}`
          : ""
      );
      setProfileImageData(updated.profileImageData || "");
      setRemoveProfileImage(false);
      setProfileMsg("Your profile has been updated.");
    } catch (e) {
      setError(e.message || "Failed to update your profile");
    }
  }

  async function submitPassword(event) {
    event.preventDefault();
    setPasswordMsg("");
    setError("");

    if (!currentPassword || !newPassword || !confirmPassword) {
      setError("Please complete all password fields.");
      return;
    }

    try {
      await changePassword({ currentPassword, newPassword, confirmPassword });
      setCurrentPassword("");
      setNewPassword("");
      setConfirmPassword("");
      setPasswordMsg("Your password has been changed.");
    } catch (e) {
      setError(e.message || "Failed to change your password");
    }
  }

  return (
    <>
      <TopBar theme={theme} onToggleTheme={onToggleTheme} activeNav="profile" onNavigate={onNavigate} />

      <div className="container profilePage">
        <div className="profileHeader">
          <div>
            <h2>User Profile</h2>
            <div className="muted">
              Update your password, program, and profile photo. Changes are saved to your account.
            </div>
          </div>
        </div>

        {loading && <div className="card">Loading your profile...</div>}

        {!loading && (
          <div className="profileGrid">
            <div className="card profileSidebar">
              <div className="profileAvatarWrap">
                {profileImageData && !removeProfileImage ? (
                  <img className="profileAvatar" src={profileImageData} alt="Profile" />
                ) : (
                  <div className="profileAvatar profileAvatarFallback">
                    {(firstName?.[0] || profile?.email?.[0] || "U").toUpperCase()}
                    {(lastName?.[0] || "").toUpperCase()}
                  </div>
                )}
              </div>

              <div className="profileSidebarDetails">
                <h3>{firstName || profile?.firstName || "User"} {lastName || profile?.lastName || ""}</h3>
                <div className="muted">{profile?.email}</div>
              </div>

              <label className="profileUpload">
                Profile photo
                <input type="file" accept="image/*" onChange={handlePhotoChange} />
              </label>

              <div className="profilePhotoActions">
                <button
                  type="button"
                  className="btn"
                  onClick={() => {
                    setProfileImageData("");
                    setRemoveProfileImage(true);
                  }}
                >
                  Remove Photo
                </button>
              </div>
            </div>

            <div className="profileMain">
              <form className="card profileCard form" onSubmit={submitProfile}>
                <h3>Profile Details</h3>

                <div className="authRow2">
                  <label>
                    First name
                    <input value={firstName} onChange={(event) => setFirstName(event.target.value)} />
                  </label>

                  <label>
                    Last name
                    <input value={lastName} onChange={(event) => setLastName(event.target.value)} />
                  </label>
                </div>

                <label>
                  Email
                  <input value={profile?.email || ""} disabled />
                </label>

                <label>
                  Faculty
                  <select
                    value={facultyId}
                    onChange={(event) => {
                      setFacultyId(event.target.value);
                      setProgramId("");
                      setProgramQuery("");
                    }}
                  >
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
                          setProgramId("");
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

                {profileMsg && <div className="success">{profileMsg}</div>}
                {error && <div className="error">{error}</div>}

                <button className="btn primary" type="submit">
                  Save Profile
                </button>
              </form>

              <form className="card profileCard form" onSubmit={submitPassword}>
                <h3>Change Password</h3>

                <label>
                  Current password
                  <input
                    type="password"
                    value={currentPassword}
                    onChange={(event) => setCurrentPassword(event.target.value)}
                    autoComplete="current-password"
                  />
                </label>

                <div className="authRow2">
                  <label>
                    New password
                    <input
                      type="password"
                      value={newPassword}
                      onChange={(event) => setNewPassword(event.target.value)}
                      autoComplete="new-password"
                    />
                  </label>

                  <label>
                    Confirm new password
                    <input
                      type="password"
                      value={confirmPassword}
                      onChange={(event) => setConfirmPassword(event.target.value)}
                      autoComplete="new-password"
                    />
                  </label>
                </div>

                {passwordMsg && <div className="success">{passwordMsg}</div>}

                <button className="btn primary" type="submit">
                  Change Password
                </button>
              </form>
            </div>
          </div>
        )}
      </div>
    </>
  );
}
