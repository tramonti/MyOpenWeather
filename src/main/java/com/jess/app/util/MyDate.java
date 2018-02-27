package com.jess.app.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MyDate {
    public MyDate() {
    }

    public static LocalDateTime getTomorrowNoon() {
        return getDayNoonAfterToday(1);
    }

    private static LocalDateTime getTodayNoon() {
        LocalTime afternoon = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, afternoon);
    }

    private static LocalDateTime getDayNoonAfterToday(int days) {
        return getTodayNoon().plusDays(days);
    }

    public static LocalDateTime getDayAfterTomorrowNoon() {
        return getDayNoonAfterToday(2);
    }

    public static LocalDateTime getSecondDayAfterTomorrow() {
        return getDayNoonAfterToday(3);
    }

    public static String getDayOfWeekString(LocalDateTime ldt) {
        String day;
        switch (ldt.getDayOfWeek()) {
            case MONDAY:
                day = "Mon";
                break;
            case TUESDAY:
                day = "Tue";
                break;
            case WEDNESDAY:
                day = "Wed";
                break;
            case THURSDAY:
                day = "Thu";
                break;
            case FRIDAY:
                day = "Fri";
                break;
            case SATURDAY:
                day = "Sat";
                break;
            case SUNDAY:
                day = "Sun";
                break;
            default:
                day = "??";
                break;
        }
        return day;
    }
}
