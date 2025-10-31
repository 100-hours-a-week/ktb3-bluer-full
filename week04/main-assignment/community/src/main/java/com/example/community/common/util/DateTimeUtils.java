package com.example.community.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateTimeUtils {
    private DateTimeUtils() {
    }

    public static LocalDateTime currentUtc() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
    }

    public static LocalDateTime parseToUtc(String timestamp, LocalDateTime fallback) {
        if (timestamp == null || timestamp.isBlank()) {
            return fallback;
        }
        try {
            return LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException ignored) {
            try {
                Instant instant = Instant.parse(timestamp);
                return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
            } catch (DateTimeParseException parseException) {
                return fallback;
            }
        }
    }

    public static String toIsoString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atOffset(ZoneOffset.UTC).toInstant().toString();
    }
}
