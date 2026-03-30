/**
 * Legacy theme toggle button.
 *
 * This component switches between the older red/black body class themes. It is
 * separate from the current light/dark theme flow and appears to be retained
 * for compatibility or experimentation.
 */
import React, { useState, useEffect } from "react";
import "../../index.css"; // updated to match your CSS file name

/**
 * Renders a floating button that toggles the legacy UI theme classes.
 */
function UIButton() {
  const [UI, setUI] = useState(localStorage.getItem("theme") || "Red");

  const toggleUI = () => {
    const newTheme = UI === "Red" ? "Black" : "Red";
    setUI(newTheme);
    localStorage.setItem("theme", newTheme);
  };

  useEffect(() => {
    // The legacy theme system relies on body classes rather than CSS variables.
    document.body.classList.remove("theme-red", "theme-black");
    document.body.classList.add(UI === "Red" ? "theme-red" : "theme-black");
  }, [UI]);

  return (
    <button
      className={`themeToggle ${UI === "Red" ? "red" : "black"}`}
      onClick={toggleUI}
    >
      Toggle UI
    </button>
  );
}

export default UIButton;
