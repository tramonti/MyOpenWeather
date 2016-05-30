package app.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by olesia on 30.05.16.
 */
public class MyDate {
    public MyDate() {
    }

    public static LocalDateTime getTomorrowNoon() {
        LocalTime afternoon = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        LocalDateTime todayNoon = LocalDateTime.of(today, afternoon);
        LocalDateTime tomorrowNoon = todayNoon.plusDays(1);
        return tomorrowNoon;

    }

    public static LocalDateTime getDayAfterTomorrowNoon() {

        LocalTime afternoon = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        LocalDateTime todayNoon = LocalDateTime.of(today, afternoon);
        LocalDateTime dayAfterTomorrowNoon = todayNoon.plusDays(2);
        return dayAfterTomorrowNoon;
    }

    public static LocalDateTime getSecondDayAfterTomorrow() {
        LocalTime afternoon = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        LocalDateTime todayNoon = LocalDateTime.of(today, afternoon);
        LocalDateTime secondDayAfterTomorrowNoon = todayNoon.plusDays(3);
        return secondDayAfterTomorrowNoon;
    }

    public static String getDayOfWeekString(LocalDateTime ldt){
        String day = "";
        switch (ldt.getDayOfWeek()){
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
                day ="Fri";
                break;
            case SATURDAY:
                day = "Sat";
                break;
            case SUNDAY:
                day = "Sun";
                break;
            default:
                day ="??";
                break;
        }
        return day;
    }
}
