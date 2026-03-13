import React, { useState, useEffect } from "react";
import "../../index.css"; // updated to match your CSS file name

function UIButton() {
  const [UI, setUI] = useState(localStorage.getItem("theme") || "Red");

  const toggleUI = () => {
    const newTheme = UI === "Red" ? "Black" : "Red";
    setUI(newTheme);
    localStorage.setItem("theme", newTheme);
  };

  useEffect(() => {
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