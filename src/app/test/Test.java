package app.test;

import com.google.gson.Gson;
import util.data.city.City;
import util.net.FetchURLData;
import util.net.WeatherParser;

import java.io.*;
import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Created by olesia on 09.05.16.
 */
public class Test {
    public static void main(String[] args) throws IOException {

        Thread t = new Thread(new Runnable() {
//            WeatherParser wp = new WeatherParser();

            @Override
            public void run() {

                    while (true) {
//                        System.out.println("I am a virus!");

                    }



            }

        });

        long startTime = System.currentTimeMillis();
        t.start();
//        System.out.println("interrupted: " + t.isInterrupted());

        try {
            while (t.isAlive()) {
                System.out.println(System.currentTimeMillis() - startTime);
                if (System.currentTimeMillis() - startTime > 1000) {

                    t.stop();

                    t = null;
//
                    System.out.println("Stopped!");
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("I am null");
        }
//        try {
//            System.out.println("try sleep");
//            t.interrupt();
//            t.sleep(10000);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("Thread is dead");


    }

}
