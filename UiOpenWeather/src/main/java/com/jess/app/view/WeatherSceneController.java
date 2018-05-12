package com.jess.app.view;

import com.jess.app.MainApp;
import com.jess.app.util.MyDate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;
import util.data.city.City;
import util.data.status.Condition;
import util.net.WeatherParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class WeatherSceneController {
    @FXML
    private Label cityName;
    @FXML
    private Label country;
    @FXML
    private Label currentTemp;
    @FXML
    private Label maxTemp;
    @FXML
    private Label minTemp;
    @FXML
    private Label windSpeed;
    /*days temperature*/
    @FXML
    private Label dayOneTemp;
    @FXML
    private Label dayTwoTemp;
    @FXML
    private Label dayThreeTemp;
    /*other info*/
    @FXML
    private Label pressure;
    @FXML
    private Label humidity;
    @FXML
    private Label weatherMain;
    @FXML
    private Label weatherDescription;
    @FXML
    private Label date;
    @FXML
    private ImageView icon;
    @FXML
    private ImageView dayOneIcon;
    @FXML
    private ImageView dayTwoIcon;
    @FXML
    private ImageView dayThreeIcon;
    @FXML
    private Arc arc;
    @FXML
    private AnchorPane root;
    /*days names*/
    @FXML
    private Label dayOneName;
    @FXML
    private Label day2name;
    @FXML
    private Label day3name;
    private MainApp mainApp;
    private City city;

    public WeatherSceneController() {
    }

    @FXML
    private void initialize() {
        Thread initThread = new Thread(() -> {
            try {
                showWeatherDetails(getCityIdFromMyFile());
            } catch (UnknownHostException e) {
                setDefault();
            }
        });
        ExecutorService threadPool = Executors.newCachedThreadPool();
        long startTime = System.currentTimeMillis();
        Future future = threadPool.submit(initThread);
        while (!future.isDone()) {
            if (System.currentTimeMillis() - startTime > 2000) {
                future.cancel(true);
                setDefault();
                break;
            }
        }
        threadPool.shutdown();
    }

    public void showWeatherDetails(long cityID) throws UnknownHostException {
        WeatherParser weatherParser = new WeatherParser();
        LocalDateTime time = LocalDateTime.now();
        City cityModel = null;
        try {
            cityModel = weatherParser.getProtectedCity(cityID);
            this.city = cityModel;
            ArrayList<Condition> conditions = cityModel.getConditionsByDate(time);
            Condition mainConditon = conditions.get(0);
            /*conditions for all days */
            Condition dayOneCondition = cityModel.getConditionByTime(MyDate.getTomorrowNoon());
            Condition dayTwoCondition = cityModel.getConditionByTime(MyDate.getDayAfterTomorrowNoon());
            Condition dayThreeCondition = cityModel.getConditionByTime(MyDate.getSecondDayAfterTomorrow());
            ArrayList<Double> maxTemperatures = new ArrayList<>();
            ArrayList<Double> minTemperatures = new ArrayList<>();
            for (Condition c : conditions) {
                maxTemperatures.add(c.getTemperature().getMaxTemp());
                minTemperatures.add(c.getTemperature().getMinTemp());
            }
            Collections.sort(maxTemperatures);
            Collections.sort(minTemperatures);
            // filling data
            cityName.setText(cityModel.getName());
            country.setText(cityModel.getCountry());
            currentTemp.setText(mainConditon.getTemperature().getTemp() + "°C");
            maxTemp.setText(maxTemperatures.get(maxTemperatures.size() - 1) + "°C");
            minTemp.setText(minTemperatures.get(0) + "°C");
            windSpeed.setText(mainConditon.getWind().getSpeed() + " m/s");
//            windDirection.setText("Dir: " + cityModel.getWindDirection());
            pressure.setText(mainConditon.getPressure().getPressure() + " hPa");
            humidity.setText(mainConditon.getHumidity() + "%");
            weatherDescription.setText(mainConditon.getWeather().getDescription());
            weatherMain.setText(mainConditon.getWeather().getMain());
//            date.setText(mainConditon.getDateTxt());
//            date.setText("");
//            //"http://openweathermap.org/img/w/10d.png"
            icon.setImage(new Image("http://openweathermap.org/img/w/" + mainConditon.getWeather().getIcon() + ".png"));
            icon.setFitWidth(20);
            icon.setFitWidth(45);
            /* filling day data */
            // images
            dayOneIcon.setImage(new Image("http://openweathermap.org/img/w/" + dayOneCondition.getWeather().getIcon() + ".png"));
            dayTwoIcon.setImage(new Image("http://openweathermap.org/img/w/" + dayTwoCondition.getWeather().getIcon() + ".png"));
            dayThreeIcon.setImage(new Image("http://openweathermap.org/img/w/" + dayThreeCondition.getWeather().getIcon() + ".png"));
            // temperature
            dayOneTemp.setText((int) dayOneCondition.getTemperature().getTemp() + "°C");
            dayTwoTemp.setText((int) dayTwoCondition.getTemperature().getTemp() + "°C");
            dayThreeTemp.setText((int) dayThreeCondition.getTemperature().getTemp() + "°C");
            // day names
            dayOneName.setText(MyDate.getDayOfWeekString(MyDate.getTomorrowNoon()));
            day2name.setText(MyDate.getDayOfWeekString(MyDate.getDayAfterTomorrowNoon()));
            day3name.setText(MyDate.getDayOfWeekString(MyDate.getSecondDayAfterTomorrow()));
        } catch (ConnectException e) {
            setDefault();
        }
    }

    @FXML
    private void updateHandle() {
        try {
            showWeatherDetails(getCityIdFromMyFile());
        } catch (UnknownHostException e) {
            setDefault();
        }
    }

    @FXML
    private void handleChangeCity() {
        boolean okClicked = mainApp.showIdEditDialog();
    }

    private void setDefault() {
        cityName.setText("??????");
        country.setText("??");
        currentTemp.setText("");
        maxTemp.setText("?");
        minTemp.setText("?");
        windSpeed.setText("?");
//            windDirection.setText("");
        pressure.setText("?");
        humidity.setText("?");
        weatherDescription.setText("Connection Lost");
        weatherMain.setText("");
        // date.setText("");
        //"http://openweathermap.org/img/w/10d.png"
        icon.setImage(new Image("images/icon.jpg"));
        icon.setFitWidth(100);
        icon.setFitHeight(80);
        // day data default fill
        // icons
        dayOneIcon.setImage(new Image("images/icon.jpg"));
        dayTwoIcon.setImage(new Image("images/icon.jpg"));
        dayThreeIcon.setImage(new Image("images/icon.jpg"));
        // temperatures
        dayOneTemp.setText("?");
        dayTwoTemp.setText("?");
        dayThreeTemp.setText("?");
        // day names
        dayOneName.setText("??");
        day2name.setText("??");
        day3name.setText("??");
    }

    private long getCityIdFromMyFile() {
        StringBuilder everything = new StringBuilder();
        try {
            File file = new File("/var/tmp/weatherId.tmp");
            if (!file.exists()) {
                file.createNewFile();
                writeIdToFile(703448);
                return 703448;
            }
            Scanner scan = new Scanner(file);
            while (scan.hasNext()) {
                if (scan.hasNextInt()) {
                    everything.append(scan.nextInt());
                }
            }
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long id = Long.parseLong(everything.toString());
        return id;
    }

    public void writeIdToFile(long id) {
        String cityId = id + "";
        try {
            File file = new File("/var/tmp/weatherId.tmp");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(cityId);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}