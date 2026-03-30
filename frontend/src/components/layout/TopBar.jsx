/**
 * Shared top navigation and account menu for the frontend.
 *
 * This component adapts its layout for authenticated and unauthenticated
 * screens, handles sticky header behavior, and exposes account navigation
 * actions such as profile access and logout.
 */
import { useEffect, useRef, useState } from "react";
import { createPortal } from "react-dom";
import { useAuth } from "../../context/AuthContext.jsx";

/**
 * Returns the best display name for the current account context.
 */
function getAccountName(profile, fallbackUsername) {
  const fullName = [profile?.firstName, profile?.lastName]
    .filter(Boolean)
    .join(" ")
    .trim();

  return fullName || fallbackUsername || "My Account";
}

/**
 * Returns initials for the account avatar fallback when no profile image is available.
 */
function getAccountInitials(profile, fallbackUsername) {
  const letters = [profile?.firstName?.[0], profile?.lastName?.[0]]
    .filter(Boolean)
    .join("")
    .toUpperCase();

  if (letters) {
    return letters.slice(0, 2);
  }

  return (fallbackUsername || "U").slice(0, 2).toUpperCase();
}

/**
 * Renders the shared site header and contextual account menu.
 */
export default function TopBar({
  theme = "dark",
  onToggleTheme,
  showOfficeChrome = true,
  showOfficeNav = true,
  stickyOfficeChrome = true,
  activeNav = "schedule",
  onNavigate,
}) {
  const { isAuthed, username, profile, logout } = useAuth();
  const [isPastTopbar, setIsPastTopbar] = useState(false);
  const [menuOpen, setMenuOpen] = useState(false);
  const [menuPosition, setMenuPosition] = useState(null);
  const accountMenuRef = useRef(null);
  const accountTriggerRef = useRef(null);
  const accountDropdownRef = useRef(null);

  const accountName = getAccountName(profile, username);
  const accountEmail = profile?.email || username || "";
  const accountInitials = getAccountInitials(profile, username);

  useEffect(() => {
    if (!showOfficeChrome || !stickyOfficeChrome) {
      setIsPastTopbar(false);
      return undefined;
    }

    // Sticky header styling changes once the user scrolls beyond the expanded topbar.
    function onScroll() {
      setIsPastTopbar(window.scrollY > 64);
    }

    onScroll();
    window.addEventListener("scroll", onScroll, { passive: true });
    return () => window.removeEventListener("scroll", onScroll);
  }, [showOfficeChrome, stickyOfficeChrome]);

  useEffect(() => {
    if (!menuOpen) {
      return undefined;
    }

    // The account menu closes on outside interaction and Escape for predictable keyboard behavior.
    function onDocumentClick(event) {
      const clickedTrigger = accountTriggerRef.current?.contains(event.target);
      const clickedDropdown = accountDropdownRef.current?.contains(event.target);
      if (!clickedTrigger && !clickedDropdown) {
        setMenuOpen(false);
      }
    }

    function onDocumentKeydown(event) {
      if (event.key === "Escape") {
        setMenuOpen(false);
      }
    }

    document.addEventListener("mousedown", onDocumentClick);
    document.addEventListener("keydown", onDocumentKeydown);

    return () => {
      document.removeEventListener("mousedown", onDocumentClick);
      document.removeEventListener("keydown", onDocumentKeydown);
    };
  }, [menuOpen]);

  useEffect(() => {
    if (!menuOpen) {
      setMenuPosition(null);
      return undefined;
    }

    // The dropdown is portaled to document.body, so its screen position must be recomputed manually.
    function updateMenuPosition() {
      const rect = accountTriggerRef.current?.getBoundingClientRect();
      if (!rect) {
        return;
      }

      setMenuPosition({
        top: rect.bottom - 1,
        left: Math.max(18, rect.left),
        width: rect.width,
      });
    }

    updateMenuPosition();
    window.addEventListener("resize", updateMenuPosition);
    window.addEventListener("scroll", updateMenuPosition, { passive: true });

    return () => {
      window.removeEventListener("resize", updateMenuPosition);
      window.removeEventListener("scroll", updateMenuPosition);
    };
  }, [menuOpen]);

  const headerClass = !showOfficeChrome
    ? "siteHeaderSimple"
    : !stickyOfficeChrome
      ? "siteHeaderAuth"
      : isPastTopbar
        ? "siteHeaderCollapsed"
        : "siteHeaderExpanded";

  return (
    <header className={`siteHeader ${headerClass}`}>
      <div className="topbar">
        <div className="brandBlock">
          <a
            className="wordmark wordmarkLink"
            aria-label="York University"
            href="https://www.yorku.ca/"
          >
            <span className="wordmarkYork">YORK</span>
            <span className="wordmarkU">U</span>
          </a>
        </div>

        <div className="right">
          {isAuthed ? (
            <div className="accountMenu" ref={accountMenuRef}>
              <button
                ref={accountTriggerRef}
                type="button"
                className={`accountTrigger ${menuOpen ? "accountTriggerOpen" : ""}`.trim()}
                aria-haspopup="menu"
                aria-expanded={menuOpen}
                onClick={() => setMenuOpen((value) => !value)}
              >
                {profile?.profileImageData ? (
                  <img className="accountAvatar" src={profile.profileImageData} alt={accountName} />
                ) : (
                  <div className="accountAvatar accountAvatarFallback" aria-hidden="true">
                    {accountInitials}
                  </div>
                )}

                <span className="accountMeta">
                  <span className="accountName">{accountName}</span>
                  <span className="accountEmail">{accountEmail || "Signed in"}</span>
                </span>

                <span className="accountChevron" aria-hidden="true">
                  {menuOpen ? "^" : "v"}
                </span>
              </button>
            </div>
          ) : (
            <span className="muted">Not signed in</span>
          )}
        </div>
      </div>

      {menuOpen && menuPosition && createPortal(
        // Portaling avoids clipping issues caused by the layered sticky header containers.
        <div
          ref={accountDropdownRef}
          className="accountDropdown"
          role="menu"
          aria-label="Account menu"
          style={{
            position: "fixed",
            top: `${menuPosition.top}px`,
            left: `${menuPosition.left}px`,
            width: `${menuPosition.width}px`,
          }}
        >
          <div className="accountDropdownActions">
            <button
              type="button"
              className="accountDropdownAction"
              onClick={() => {
                setMenuOpen(false);
                onNavigate?.("/profile");
              }}
            >
              Profile
            </button>
            <button
              type="button"
              className="accountDropdownAction"
              onClick={() => {
                setMenuOpen(false);
                logout();
              }}
            >
              Log Out
            </button>
          </div>
        </div>,
        document.body
      )}

      {showOfficeChrome && (
        <div className={`officeChrome ${stickyOfficeChrome ? "" : "officeChromeInline"}`}>
          <div className="officeBar">YU Path Builder</div>
          {showOfficeNav && (
            <nav className="officeNav" aria-label="Registrar navigation">
              <button
                type="button"
                className={`officeLink officeNavBtn ${activeNav === "profile" ? "officeLinkActive" : ""}`.trim()}
                onClick={() => onNavigate?.("/profile")}
              >
                User Profile
              </button>
              <button
                type="button"
                className={`officeLink officeNavBtn ${activeNav === "schedule" ? "officeLinkActive" : ""}`.trim()}
                onClick={() => onNavigate?.("/")}
              >
                Build My Schedule
              </button>
              <a className="officeLink" href="mailto:support@yupathbuilder.local">
                Contact
              </a>
            </nav>
          )}
        </div>
      )}
    </header>
  );
}
