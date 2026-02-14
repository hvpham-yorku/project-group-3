/**
 * Legacy CRA-style entry file (likely unused in Vite).
 * Vite uses src/main.jsx as the entrypoint.
 */

import React from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";

const root = createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

reportWebVitals();
