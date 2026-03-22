const DAY_DEFS = [
  { short: "Mon", label: "Monday", pluralLabel: "Mondays", key: "MON" },
  { short: "Tue", label: "Tuesday", pluralLabel: "Tuesdays", key: "TUE" },
  { short: "Wed", label: "Wednesday", pluralLabel: "Wednesdays", key: "WED" },
  { short: "Thu", label: "Thursday", pluralLabel: "Thursdays", key: "THU" },
  { short: "Fri", label: "Friday", pluralLabel: "Fridays", key: "FRI" },
];

const DAY_LOOKUP = Object.fromEntries(DAY_DEFS.map((day) => [day.key, day]));

export function toMinutes(value) {
  if (!value) return null;
  const parts = String(value).split(":");
  const hh = Number(parts[0] ?? 0);
  const mm = Number(parts[1] ?? 0);
  return hh * 60 + mm;
}

export function parseScheduleDays(value) {
  if (!value) return [];
  const raw = String(value).trim();

  if (raw.includes(",") || /mon|tue|wed|thu|fri/i.test(raw)) {
    return raw
      .split(/[,\s]+/)
      .map((token) => token.trim().toLowerCase())
      .filter(Boolean)
      .map((token) => {
        if (token.startsWith("mon")) return "MON";
        if (token.startsWith("tue")) return "TUE";
        if (token.startsWith("wed")) return "WED";
        if (token.startsWith("thu")) return "THU";
        if (token.startsWith("fri")) return "FRI";
        return null;
      })
      .filter(Boolean);
  }

  const compact = [];
  for (const ch of raw.toUpperCase()) {
    if (ch === "M") compact.push("MON");
    if (ch === "T") compact.push("TUE");
    if (ch === "W") compact.push("WED");
    if (ch === "R") compact.push("THU");
    if (ch === "F") compact.push("FRI");
  }
  return compact;
}

export function getScheduleDays() {
  return DAY_DEFS;
}

export function formatMinutes(mins) {
  const hh = Math.floor(mins / 60);
  const mm = mins % 60;
  return `${hh}:${String(mm).padStart(2, "0")}`;
}

export function formatRange(start, end) {
  return `${formatMinutes(start)} - ${formatMinutes(end)}`;
}

export function formatDayLabel(dayKey, plural = false) {
  const day = DAY_LOOKUP[dayKey];
  if (!day) return dayKey;
  return plural ? day.pluralLabel : day.label;
}

export function buildOccurrenceId(section, dayKey) {
  return [
    section.courseCode,
    section.sectionId,
    dayKey,
    section.startTime,
    section.endTime,
    section.location ?? "",
  ].join("|");
}

export function expandChosenSections(chosenSections = [], visibleRange) {
  const rangeStart = visibleRange?.start ?? Number.NEGATIVE_INFINITY;
  const rangeEnd = visibleRange?.end ?? Number.POSITIVE_INFINITY;
  const list = Array.isArray(chosenSections) ? chosenSections : [];
  const occurrences = [];

  for (const section of list) {
    const start = toMinutes(section.startTime);
    const end = toMinutes(section.endTime);
    const days = parseScheduleDays(section.days);

    if (start == null || end == null || end <= start || days.length === 0) continue;

    const clampedStart = Math.max(start, rangeStart);
    const clampedEnd = Math.min(end, rangeEnd);
    if (clampedEnd <= clampedStart) continue;

    for (const dayKey of days) {
      occurrences.push({
        ...section,
        eventId: buildOccurrenceId(section, dayKey),
        day: dayKey,
        start: clampedStart,
        end: clampedEnd,
      });
    }
  }

  return occurrences;
}

export function detectScheduleConflicts(chosenSections = [], visibleRange) {
  const occurrences = expandChosenSections(chosenSections, visibleRange);
  const byDay = new Map();

  for (const occurrence of occurrences) {
    if (!byDay.has(occurrence.day)) byDay.set(occurrence.day, []);
    byDay.get(occurrence.day).push(occurrence);
  }

  const segments = [];

  for (const day of DAY_DEFS) {
    const dayOccurrences = byDay.get(day.key) ?? [];
    if (dayOccurrences.length < 2) continue;

    const breakpoints = [...new Set(dayOccurrences.flatMap((item) => [item.start, item.end]))]
      .sort((a, b) => a - b);

    for (let i = 0; i < breakpoints.length - 1; i += 1) {
      const start = breakpoints[i];
      const end = breakpoints[i + 1];
      if (end <= start) continue;

      const overlapping = dayOccurrences
        .filter((item) => item.start < end && item.end > start)
        .sort((a, b) => a.courseCode.localeCompare(b.courseCode));

      if (overlapping.length < 2) continue;

      const signature = overlapping.map((item) => item.eventId).join("||");
      const previous = segments[segments.length - 1];

      if (
        previous &&
        previous.day === day.key &&
        previous.signature === signature &&
        previous.end === start
      ) {
        previous.end = end;
        continue;
      }

      segments.push({
        day: day.key,
        start,
        end,
        signature,
        occurrences: overlapping,
      });
    }
  }

  const conflictEventIds = new Set();
  for (const segment of segments) {
    for (const occurrence of segment.occurrences) {
      conflictEventIds.add(occurrence.eventId);
    }
  }

  return {
    hasConflicts: segments.length > 0,
    segments,
    conflictEventIds,
  };
}
