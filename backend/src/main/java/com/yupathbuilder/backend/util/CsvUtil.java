package com.yupathbuilder.backend.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Minimal CSV reader for "Excel-edited CSV" (no quotes/commas inside fields).
 * For ITR1/MVP fake data this is usually enough.
 */
public final class CsvUtil {

    private CsvUtil() {}

    public static List<String[]> readAll(InputStream in) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                // skip comments
                if (line.trim().startsWith("#")) continue;
                rows.add(split(line));
            }
        }
        return rows;
    }

    private static String[] split(String line) {
        // Very simple: split by comma and trim
        String[] parts = line.split(",", -1);
        for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
        return parts;
    }
}
