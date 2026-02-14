import React, { useMemo } from "react";

/**
 * York-style weekly schedule table:
 * - Time column on the left (30-min rows)
 * - Day columns (Mon..Fri)
 * - Events are positioned inside each day column using absolute top/height
 */
export default function ScheduleGrid({ chosenSections }) {
  const DAYS = ["Mon", "Tue", "Wed", "Thu", "Fri"];
  const DAY_KEY = { Mon: "MON", Tue: "TUE", Wed: "WED", Thu: "THU", Fri: "FRI" };

  // Table range (8:00 - 19:00)
  const START_DAY_MIN = 8 * 60;
  const END_DAY_MIN = 19 * 60;

  // Must match CSS: .timeCell / .gridRow height (px)
  const SLOT_MINUTES = 30;
  const SLOT_PX = 30;

  function toMin(t) {
    if (!t) return null;
    // accepts "HH:MM" or "HH:MM:SS"
    const parts = String(t).split(":");
    const hh = Number(parts[0] ?? 0);
    const mm = Number(parts[1] ?? 0);
    return hh * 60 + mm;
  }

  function parseDays(str) {
    if (!str) return [];
    const s = String(str).trim();

    // support "Mon,Wed" or "Mon Wed"
    if (s.includes(",") || s.toLowerCase().includes("mon")) {
      return s
        .split(/[,\s]+/)
        .map((x) => x.trim().toLowerCase())
        .filter(Boolean)
        .map((x) => {
          if (x.startsWith("mon")) return "MON";
          if (x.startsWith("tue")) return "TUE";
          if (x.startsWith("wed")) return "WED";
          if (x.startsWith("thu")) return "THU";
          if (x.startsWith("fri")) return "FRI";
          return null;
        })
        .filter(Boolean);
    }

    // support compact "MWTRF" style (M T W R F)
    const out = [];
    for (const ch of s.toUpperCase()) {
      if (ch === "M") out.push("MON");
      if (ch === "T") out.push("TUE");
      if (ch === "W") out.push("WED");
      if (ch === "R") out.push("THU"); // York uses R for Thursday
      if (ch === "F") out.push("FRI");
    }
    return out;
  }

  const events = useMemo(() => {
    const list = Array.isArray(chosenSections) ? chosenSections : [];
    const out = [];

    for (const sec of list) {
      const start = toMin(sec.startTime);
      const end = toMin(sec.endTime);
      const ds = parseDays(sec.days);

      if (start == null || end == null || ds.length === 0) continue;

      // clamp to visible range
      const s = Math.max(start, START_DAY_MIN);
      const e = Math.min(end, END_DAY_MIN);
      if (e <= s) continue;

      for (const d of ds) out.push({ ...sec, day: d, start: s, end: e });
    }

    return out;
  }, [chosenSections]);

  const timeLabels = useMemo(() => {
    const labels = [];
    for (let m = START_DAY_MIN; m <= END_DAY_MIN; m += SLOT_MINUTES) {
      const hh = String(Math.floor(m / 60)).padStart(2, "0");
      const mm = String(m % 60).padStart(2, "0");
      labels.push(`${Number(hh)}:${mm}`); // "8:00" instead of "08:00"
    }
    return labels;
  }, []);

  function minutesToPx(min) {
    return ((min - START_DAY_MIN) / SLOT_MINUTES) * SLOT_PX;
  }

  function eventStyle(e) {
    const top = minutesToPx(e.start);
    const height = Math.max(18, minutesToPx(e.end) - minutesToPx(e.start));
    return { top: `${top}px`, height: `${height}px` };
  }

  function renderEventsForDay(dayLabel) {
    const key = DAY_KEY[dayLabel];
    return events
      .filter((e) => e.day === key)
      .map((e) => (
        <div
          key={`${e.sectionId}-${e.day}`}
          className="schedEvent"
          style={eventStyle(e)}
          title={`${e.courseCode} ${e.sectionId} @ ${e.location || ""}`}
        >
          <div className="schedCode">{e.courseCode}</div>
          <div className="schedMeta">
            {String(e.startTime).slice(0, 5)}–{String(e.endTime).slice(0, 5)}
          </div>
          {e.location ? <div className="schedMeta">{e.location}</div> : null}
        </div>
      ));
  }

  // number of 30-min rows (END is inclusive in labels, so rows are labels-1)
  const rows = timeLabels.length - 1;

  return (
    <div className="schedWrap">
      <div className="schedTable">
        {/* Header */}
        <div className="schedHeaderRow">
          <div className="schedTimeHead">Time</div>
          {DAYS.map((d) => (
            <div key={d} className="schedDayHead">
              {d}
            </div>
          ))}
        </div>

        {/* Body */}
        <div className="schedBodyRow">
          {/* Time Column */}
          <div className="schedTimeCol">
            {timeLabels.slice(0, -1).map((t) => (
              <div key={t} className="timeCell">
                <span>{t}</span>
              </div>
            ))}
          </div>

          {/* Day Columns */}
          {DAYS.map((d) => (
            <div key={d} className="schedDayCol">
              {Array.from({ length: rows }).map((_, i) => (
                <div key={i} className="gridRow" />
              ))}
              {renderEventsForDay(d)}
            </div>
          ))}
        </div>
      </div>

      <div className="muted tiny" style={{ marginTop: 10 }}>
        Tip: if a course has no sections for the selected term in <code>sections.xlsx</code>, schedule building will fail.
      </div>
    </div>
  );
}
