package ru.qa.summer.support.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@UtilityClass
public class DateUtil {
    public static String formatCurrentDate(String pattern) {
        return new SimpleDateFormat(pattern).format(now());
    }

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(date);
    }

    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    public static java.sql.Date toSql(Date date) {
        return java.sql.Date.valueOf(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public static Date nextWorkDateOrToday() {
        return nextWorkDateOrSelf(Calendar.getInstance(Locale.FRANCE));
    }

    public static Date nextWorkDate() {
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return nextWorkDateOrSelf(calendar);
    }

    public static Date nextWorkDateOrSelf(Calendar calendar) {
        int days = 5;
        for (int i = 0; i < days; i++) {
            if (calendar.get(Calendar.DAY_OF_WEEK) > 1 && calendar.get(Calendar.DAY_OF_WEEK) <= 6) {
                break;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return calendar.getTime();
    }

    public static Date tomorrow() {
        return Date.from(ZonedDateTime.now().plusDays(1).toInstant());
    }

    public static Date yesterday() {
        return Date.from(ZonedDateTime.now().minusDays(1).toInstant());
    }

    public static Date nextWeek() {
        return Date.from(ZonedDateTime.now().plusWeeks(1).toInstant());
    }

    public static Date previousWeek() {
        return Date.from(ZonedDateTime.now().minusWeeks(1).toInstant());
    }

    public static Date fromCurrent(int days) {
        return Date.from(ZonedDateTime.now().plusDays(days).toInstant());
    }

    public static Date getDateFromString(@NotNull String stringDate, @NotNull String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(stringDate);
    }
}
