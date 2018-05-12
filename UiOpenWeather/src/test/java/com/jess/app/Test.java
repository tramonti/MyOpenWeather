package com.jess.app;

import com.jess.app.util.MyDate;
import util.data.city.City;
import util.net.WeatherParser;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by olesia on 09.05.16.
 */
public class Test {
    public static void main(String[] args) throws IOException {

        LocalTime afternoon = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        LocalDateTime todayNoon = LocalDateTime.of(today, afternoon);
        LocalDateTime tomorrowNoon = todayNoon.plusDays(1);


        System.out.printf("noon: %s\ntoday: %s\ntoday noon: %s\ntomorrow noon %s, day is: %s\n",afternoon,today,todayNoon, tomorrowNoon, tomorrowNoon.getDayOfWeek());
        System.out.println(LocalDateTime.now());
        System.out.println(MyDate.getDayAfterTomorrowNoon());

        WeatherParser weatherParser = new WeatherParser();
        LocalDateTime time = LocalDateTime.now();
        City cityModel = null;
        cityModel = weatherParser.getCityObject(703448);
        //System.out.println(cityModel);
        System.out.println(MyDate.getTomorrowNoon().getHour());

        System.out.println(cityModel.getConditionByTime(MyDate.getDayAfterTomorrowNoon()));
//        System.out.println(MyDate.getDayOfWeekString(MyDate.getSecondDayAfterTomorrow()));
    }

}
