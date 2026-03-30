/**
 * Schedule grid renderer for built schedules.
 *
 * This component transforms chosen section data into a calendar-style matrix,
 * highlights conflicts, and exposes display controls such as sizing, printing,
 * and fullscreen mode.
 */
import React, { useEffect, useMemo, useRef, useState } from "react";
import {
  detectScheduleConflicts,
  expandChosenSections,
  formatRange,
  getScheduleDays,
} from "../utils/scheduleConflicts.js";

/**
 * Compares slot states so adjacent rows with identical meaning can be merged
 * into a single rendered table cell.
 */
function sameState(a, b) {
  if (!a || !b || a.type !== b.type) return false;
  if (a.type === "empty") return false;
  if (a.type === "event") return a.eventId === b.eventId;
  return a.signature === b.signature;
}

/**
 * Renders a time-grid view of chosen sections for a single academic term.
 */
export default function ScheduleGrid({ chosenSections, termLabel }) {
  const DAYS = getScheduleDays();
  const [isFullscreen, setIsFullscreen] = useState(false);
  const [sizeIndex, setSizeIndex] = useState(() => {
    const saved = window.localStorage.getItem("schedule-size-index");
    const parsed = Number(saved);
    return Number.isInteger(parsed) ? Math.min(2, Math.max(0, parsed)) : 1;
  });
  const wrapRef = useRef(null);
  const sizeOptions = ["compact", "default", "expanded"];

  const START_DAY_MIN = 8 * 60;
  const END_DAY_MIN = 19 * 60;
  const SLOT_MINUTES = 30;

  // Slots define the visible half-hour rows that the grid can render.
  const slots = useMemo(() => {
    const list = [];
    for (let start = START_DAY_MIN; start < END_DAY_MIN; start += SLOT_MINUTES) {
      const end = start + SLOT_MINUTES;
      list.push({
        start,
        end,
        label: formatRange(start, end),
      });
    }
    return list;
  }, []);

  const events = useMemo(
    () =>
      expandChosenSections(chosenSections, {
        start: START_DAY_MIN,
        end: END_DAY_MIN,
      }),
    [chosenSections]
  );

  const conflicts = useMemo(
    () =>
      detectScheduleConflicts(chosenSections, {
        start: START_DAY_MIN,
        end: END_DAY_MIN,
      }),
    [chosenSections]
  );

  // Active days receive more width so sparse schedules stay readable.
  const activeDays = useMemo(() => {
    const used = new Set(events.map((event) => event.day));
    return DAYS.map((day) => used.has(day.key));
  }, [events]);

  const dayWidths = useMemo(() => {
    const weights = activeDays.map((isActive) => (isActive ? 1.15 : 0.72));
    const total = weights.reduce((sum, weight) => sum + weight, 0);
    return weights.map((weight) => `${(weight / total) * 100}%`);
  }, [activeDays]);

  const dayBlocks = useMemo(() => {
    const byDay = {};

    for (const day of DAYS) {
      const dayEvents = events.filter((event) => event.day === day.key);
      const dayConflicts = conflicts.segments.filter((segment) => segment.day === day.key);

      const slotStates = slots.map((slot) => {
        const overlapEvents = dayEvents.filter(
          (event) => event.start < slot.end && event.end > slot.start
        );
        const overlapConflicts = dayConflicts.filter(
          (segment) => segment.start < slot.end && segment.end > slot.start
        );

        if (overlapConflicts.length > 0 || overlapEvents.length > 1) {
          const signature =
            overlapConflicts[0]?.signature ??
            overlapEvents.map((event) => event.eventId).sort().join("||");
          return {
            type: "conflict",
            signature,
            occurrences: overlapConflicts[0]?.occurrences ?? overlapEvents,
          };
        }

        if (overlapEvents.length === 1) {
          return {
            type: "event",
            eventId: overlapEvents[0].eventId,
            event: overlapEvents[0],
          };
        }

        return { type: "empty" };
      });

      const starts = new Map();
      const covered = new Set();

      let index = 0;
      while (index < slotStates.length) {
        const state = slotStates[index];
        let endIndex = index + 1;
        while (endIndex < slotStates.length && sameState(state, slotStates[endIndex])) {
          endIndex += 1;
        }

        const block = {
          ...state,
          startIndex: index,
          rowSpan: endIndex - index,
        };
        starts.set(index, block);

        for (let coveredIndex = index + 1; coveredIndex < endIndex; coveredIndex += 1) {
          covered.add(coveredIndex);
        }

        index = endIndex;
      }

      byDay[day.key] = { starts, covered };
    }

    return byDay;
  }, [DAYS, events, conflicts, slots]);

  useEffect(() => {
    function syncFullscreen() {
      setIsFullscreen(Boolean(document.fullscreenElement));
    }

    document.addEventListener("fullscreenchange", syncFullscreen);
    return () => document.removeEventListener("fullscreenchange", syncFullscreen);
  }, []);

  useEffect(() => {
    window.localStorage.setItem("schedule-size-index", String(sizeIndex));
  }, [sizeIndex]);

  /**
   * Toggles fullscreen mode for the schedule grid wrapper when supported by the
   * browser.
   */
  async function toggleFullscreen() {
    try {
      if (!document.fullscreenElement && wrapRef.current?.requestFullscreen) {
        await wrapRef.current.requestFullscreen();
        return;
      }

      if (document.fullscreenElement && document.exitFullscreen) {
        await document.exitFullscreen();
      }
    } catch (error) {
      console.error("Fullscreen toggle failed", error);
    }
  }

  /**
   * Opens the browser print flow for the current schedule view.
   */
  function printSchedule() {
    window.print();
  }

  /**
   * Returns the merged table cell for a day/slot position, or {@code null}
   * when that position is already covered by a rowspan from an earlier slot.
   */
  function renderBlock(dayKey, slotIndex) {
    const dayInfo = dayBlocks[dayKey];
    if (dayInfo.covered.has(slotIndex)) return null;

    const block = dayInfo.starts.get(slotIndex);
    if (!block || block.type === "empty") {
      return <td className="schedMatrixCell" />;
    }

    if (block.type === "conflict") {
      return (
        <td className="schedMatrixCell schedConflictCell" rowSpan={block.rowSpan}>
          CONFLICT
        </td>
      );
    }

    return (
      <td className="schedMatrixCell schedCourseCell" rowSpan={block.rowSpan}>
        <div className="schedCode">{block.event.courseCode}</div>
        <div className="schedMeta">
          {String(block.event.startTime).slice(0, 5)}-{String(block.event.endTime).slice(0, 5)}
        </div>
        {block.event.location ? <div className="schedMeta">{block.event.location}</div> : null}
      </td>
    );
  }

  return (
    <div
      ref={wrapRef}
      className={`schedWrap schedScale${sizeOptions[sizeIndex][0].toUpperCase()}${sizeOptions[sizeIndex].slice(1)} ${isFullscreen ? "schedWrapFullscreen" : ""}`}
    >
      <div className="schedToolbar">
        <div className="schedToolbarMeta muted">
          {termLabel ? (
            <>
              Term: <b>{termLabel}</b>
            </>
          ) : null}
        </div>

        <div className="schedActions">
          <div className="schedZoomControls" aria-label="Schedule size controls">
            <button
              className="btn schedZoomBtn"
              onClick={() => setSizeIndex((value) => Math.max(0, value - 1))}
              disabled={sizeIndex === 0}
              aria-label="Make schedule smaller"
            >
              -
            </button>
            <button
              className="btn schedZoomBtn"
              onClick={() => setSizeIndex((value) => Math.min(sizeOptions.length - 1, value + 1))}
              disabled={sizeIndex === sizeOptions.length - 1}
              aria-label="Make schedule larger"
            >
              +
            </button>
          </div>
          <button className="btn" onClick={toggleFullscreen}>
            {isFullscreen ? "Exit Full Screen" : "Full Screen"}
          </button>
          <button className="btn" onClick={printSchedule}>
            Printable Version
          </button>
        </div>
      </div>

      <div className="schedTable">
        <table className="schedMatrix">
          <colgroup>
            <col className="schedTimeColumn" />
            {DAYS.map((day, index) => (
              <col key={day.key} style={{ width: dayWidths[index] }} />
            ))}
          </colgroup>

          <thead>
            <tr>
              <th className="schedTimeHead">Time</th>
              {DAYS.map((day, index) => (
                <th
                  key={day.key}
                  className={`schedDayHead ${activeDays[index] ? "schedDayHeadActive" : "schedDayHeadEmpty"}`}
                >
                  {day.label}
                </th>
              ))}
            </tr>
          </thead>

          <tbody>
            {slots.map((slot, slotIndex) => (
              <tr key={slot.label}>
                <th className="timeCell">
                  <span>{slot.label}</span>
                </th>

                {DAYS.map((day) => (
                  <React.Fragment key={`${day.key}-${slot.label}`}>
                    {renderBlock(day.key, slotIndex)}
                  </React.Fragment>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="muted tiny" style={{ marginTop: 10 }}>
        Tip: if a course has no sections for the selected term in <code>sections.xlsx</code>, schedule building will fail.
      </div>
    </div>
  );
}
