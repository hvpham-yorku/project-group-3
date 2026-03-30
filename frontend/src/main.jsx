/**
 * Frontend bootstrap module.
 *
 * This file mounts the root React application, installs global styles, and
 * wraps the component tree with shared providers needed across the app.
 */
import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css"
import { AuthProvider } from "./context/AuthContext.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    {/* AuthProvider makes session state available to the full frontend tree. */}
    <AuthProvider>
      <App />
    </AuthProvider>
  </React.StrictMode>
);
