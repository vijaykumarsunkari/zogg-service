package com.zogg.zoggservice.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeUtils {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String YYYY_MM_DD_FORMAT_TEXT = "yyyy-MM-dd";

    public static LocalDateTime parse(CharSequence text) {
        return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parseWithFormat(
            CharSequence text, DateTimeFormatter dateTimeFormatter) {
        return LocalDateTime.parse(text, dateTimeFormatter);
    }

    public static SimpleDateFormat dateTimeFormatter() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf;
    }

    public static String getCurrentDateTime() {
        String dateTime = dateTimeFormatter().format(new Date());
        return dateTime;
    }

    public static LocalDateTime getEndOfDay(LocalDateTime dateTime) {
        return dateTime.toLocalDate().atTime(LocalTime.MAX);
    }

    public static String getFormattedDateTime(String date, String format) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

    public static LocalDateTime getCurrentDateTimeInUTC() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static LocalDateTime getCurrentDateTimeInIST() {
        return LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }

    public static LocalDate getCurrentDateInIST() {
        return LocalDate.now(ZoneId.of("Asia/Kolkata"));
    }

    public static LocalDateTime convertIstToUtcLocalDateTime(Date istDate) {
        // Define the IST time zone
        ZoneId istZoneId = ZoneId.of("Asia/Kolkata");

        // Convert Date to ZonedDateTime in IST
        ZonedDateTime istZonedDateTime = istDate.toInstant().atZone(istZoneId);

        // Convert to UTC
        ZonedDateTime utcZonedDateTime = istZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));

        // Return the LocalDateTime in UTC
        return utcZonedDateTime.toLocalDateTime();
    }

    public static LocalDate convertIstDateToLocalDateTime(Date istDate) {
        // Define the IST time zone
        ZoneId istZoneId = ZoneId.of("Asia/Kolkata");

        // Convert Date to ZonedDateTime in IST
        ZonedDateTime istZonedDateTime = istDate.toInstant().atZone(istZoneId);

        // Return the LocalDateTime in UTC
        return istZonedDateTime.toLocalDate();
    }

    public static LocalDateTime addDaysToCurrentDate(int days) {
        return LocalDateTime.now(ZoneOffset.UTC).plusDays(days);
    }

    public static String getFormattedDateTime(LocalDateTime date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    public static String getFormattedDateTime(LocalDate date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    public static boolean isValidDateFormat(String taskDate) {
        try {
            LocalDate.parse(taskDate, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
