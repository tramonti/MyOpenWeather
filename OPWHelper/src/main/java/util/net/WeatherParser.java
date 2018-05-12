package util.net;

import com.google.gson.*;
import util.data.city.City;
import util.data.city.Coordinates;
import util.data.status.*;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by olesia on 06.05.16.
 * WeatherParser creates City Object and fills all useful data from jason
 */
public class WeatherParser {

    /*constructor*/
    public WeatherParser() {

    }

    /*methods*/


    /**
     * @param cityId you can find your city id here
     *               <a href="http://openweathermap.org/city">OpenWeather</a>
     *               for example: http://openweathermap.org/city/696050 -
     *               last code is a city id
     * @return City Object with all data filled in
     * @deprecated use  {@link #getProtectedCity(long)} instead
     */
    @Deprecated
    public City getCityObject(long cityId) throws ConnectException {
        FetchURLData fud = new FetchURLData();
        //create json object
        // 702550 - Lviv
        // 696050 - kiev
        //"http://api.openweathermap.org/data/2.5/forecast/city?id=702550&units=metric&APPID=713d9a6a9e5940714f0f60d0f56a095d"
        String key = "713d9a6a9e5940714f0f60d0f56a095d";
        String request = "http://api.openweathermap.org/data/2.5/forecast/city?id="
                + cityId + "&units=metric&APPID=" + key;

        // WeatherParser wp = new WeatherParser();
        String json = fud.createJSON(request);
        JsonObject jObj = createJsonObject(json);
        // System.out.println(jObj.get("city"));
        // find city
        JsonObject cityJson = jObj.getAsJsonObject("city");
//        System.out.println(cityJson.get("id"));
        // extract info about city
        long id = cityJson.get("id").getAsLong();
        String name = cityJson.get("name").getAsString();
        String country = cityJson.get("country").getAsString();
        float lon = cityJson.get("coord").getAsJsonObject().get("lon").getAsFloat();
        float lat = cityJson.get("coord").getAsJsonObject().get("lat").getAsFloat();
        // System.out.printf("id: %d\nname: %s\ncoord: lon - %f, lat - %f\ncountry: %s\n",id,name,lon,lat,country);


        // getting list of conditions
        JsonArray jarr = (JsonArray) jObj.get("list");
        ArrayList<Condition> conditions = new ArrayList<Condition>();

        for (int i = 0; i < jarr.size(); i++) {
            // extract data
            long unixDate = jarr.get(i).getAsJsonObject().get("dt").getAsLong();
            // temperature
            double temp = jarr.get(i).getAsJsonObject().get("main")
                    .getAsJsonObject().get("temp").getAsDouble();
            double minTemp = jarr.get(i).getAsJsonObject()
                    .get("main").getAsJsonObject().get("temp_min").getAsDouble();
            double maxTemp = jarr.get(i).getAsJsonObject().get("main")
                    .getAsJsonObject().get("temp_max").getAsDouble();

            Temperature temperature = new Temperature(Optional.of(temp),
                    Optional.of(minTemp), Optional.of(maxTemp));

            // pressure
            double press = jarr.get(i).getAsJsonObject().get("main").getAsJsonObject().get("pressure").getAsDouble();
            double seePress = jarr.get(i).getAsJsonObject().get("main").getAsJsonObject().get("sea_level").getAsDouble();
            double grdPress = jarr.get(i).getAsJsonObject().get("main").getAsJsonObject().get("grnd_level").getAsDouble();
            Pressure pressure = new Pressure(Optional.of(press),
                    Optional.of(seePress), Optional.of(grdPress));

            // humidity
            int humidity = jarr.get(i).getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsInt();
            // wind
            double speed = 0.0;
            double direction = 0.0;
            if (!jarr.get(i).getAsJsonObject().get("wind").isJsonNull()) {
                speed = jarr.get(i).getAsJsonObject().get("wind").getAsJsonObject().get("speed").getAsDouble();
                direction = jarr.get(i).getAsJsonObject().get("wind").getAsJsonObject().get("deg").getAsDouble();

            }

            Wind wind = new Wind(Optional.of(speed), Optional.of(direction));
            // weather
            int wId = jarr.get(i).getAsJsonObject().get("weather").getAsJsonArray()
                    .get(0).getAsJsonObject().get("id").getAsInt();
            String wMain = jarr.get(i).getAsJsonObject().get("weather").getAsJsonArray()
                    .get(0).getAsJsonObject().get("main").getAsString();
            String wDesc = jarr.get(i).getAsJsonObject().get("weather").getAsJsonArray()
                    .get(0).getAsJsonObject().get("description").getAsString();
            String wIcon = jarr.get(i).getAsJsonObject().get("weather").getAsJsonArray()
                    .get(0).getAsJsonObject().get("icon").getAsString();
            Weather weather = new Weather(Optional.of(wId), Optional.of(wMain),
                    Optional.of(wDesc), Optional.of(wIcon));
            //date
            String dateTxt = jarr.get(i).getAsJsonObject().get("dt_txt").getAsString();

            // condition
            // condition
            Condition con = new Condition();
            con.setUnixDate(Optional.of(unixDate));
            con.setTemperature(Optional.of(temperature));
            con.setPressure(Optional.of(pressure));
            con.setHumidity(Optional.of(humidity));
            con.setWind(Optional.of(wind));
            con.setWeather(Optional.of(weather));
            con.setDateTxt(Optional.of(dateTxt));

            // add to array
            conditions.add(con);

        }
        // creating city object
        City city = new City(Optional.of(id), Optional.of(name),
                Optional.of(new Coordinates(Optional.of(lon), Optional.of(lat))),
                Optional.of(country), conditions);
        return city;
    }

    /**
     * @param cityId you can find your city id here
     *               <a href="http://openweathermap.org/city">OpenWeather</a>
     *               for example: http://openweathermap.org/city/696050 -
     *               last code is a city id
     * @return City Object with all data filled in
     * and default city if an error occurs
     */
    public City getProtectedCity(long cityId) throws ConnectException {
//        default city
        City city = new City(Optional.<Long>empty(), Optional.<String>empty(),
                Optional.<Coordinates>empty(), Optional.<String>empty(),
                null);
        long id = 0;
        String name = null;
        String country = null;
        float lon;
        float lat;
        Coordinates coordinates = new Coordinates(Optional.<Float>empty(),
                Optional.<Float>empty());
        ArrayList<Condition> conditions = new ArrayList<>(1);

        FetchURLData fud = new FetchURLData();
        String key = "713d9a6a9e5940714f0f60d0f56a095d";
        String request = "http://api.openweathermap.org" +
                "/data/2.5/forecast?id="
                + cityId + "&APPID=" + key+"&units=metric";
        // WeatherParser wp = new WeatherParser();
        String json = fud.createJSON(request);
        JsonObject jObj;

        if (createJsonObject(json) != null) {
            jObj = createJsonObject(json);
            JsonObject cityJson;

            if (!jObj.getAsJsonObject("city").isJsonNull()) {
                cityJson = jObj.getAsJsonObject("city");

                id = getFromJsonLong(cityJson, "id");
                name = getFromJsonString(cityJson, "name");
                country = getFromJsonString(cityJson, "country");

                if (!cityJson.get("coord").isJsonNull()) {
                    JsonObject cor = cityJson.get("coord").getAsJsonObject();

                    lon = getFromJsonFloat(cor, "lon");
                    lat = getFromJsonFloat(cor, "lat");
                    coordinates = new Coordinates(Optional.of(lon),
                            Optional.of(lat));
                }
            }
            JsonArray jArray;
            if (!jObj.get("list").isJsonNull()) {
                jArray = (JsonArray) jObj.get("list");

                conditions = getCityConditions(jArray);
            }

            city = new City(Optional.of(id), Optional.of(name),
                    Optional.of(coordinates),
                    Optional.of(country), conditions);

        }
        return city;
    }


    private JsonObject createJsonObject(String json) {
        JsonObject obj = null;
        JsonParser parser = new JsonParser();
        Pattern p = Pattern.compile("^(.*?(\\bcity\\b)[^$]*)$");
        Matcher matcher = p.matcher(json);
        if (matcher.matches()) {
            if (!parser.parse(json).isJsonNull()) {
                obj = parser.parse(json).getAsJsonObject();
            }
        }
        return obj;
    }

    private long getFromJsonLong(JsonObject jsonObject, String match) {

        if (!jsonObject.get(match).isJsonNull())
            return jsonObject.get(match).getAsLong();

        else return (long) 0;
    }

    private String getFromJsonString(JsonObject jsonObject, String match) {

        if (!jsonObject.get(match).isJsonNull())
            return jsonObject.get(match).getAsString();

        else return "Sorry...";
    }

    private double getFromJsonDouble(JsonObject jsonObject, String match) {

        if (!jsonObject.get(match).isJsonNull())
            return jsonObject.get(match).getAsDouble();

        else return 0.0;
    }

    private float getFromJsonFloat(JsonObject jsonObject, String match) {

        if (!jsonObject.get(match).isJsonNull())
            return jsonObject.get(match).getAsFloat();

        else return 0;
    }

    private int getFromJsonInteger(JsonObject jsonObject, String match) {

        if (!jsonObject.get(match).isJsonNull())
            return jsonObject.get(match).getAsInt();

        else return 0;
    }

    private ArrayList<Condition> getCityConditions(JsonArray jArray) {
        ArrayList<Condition> conditions = new ArrayList<>();

        for (int i = 0; i < jArray.size(); i++) {
            JsonObject element;

            long unixDate;
            double temp;
            double minTemp;
            double maxTemp;
            Temperature temperature = new Temperature(Optional.<Double>empty(),
                    Optional.<Double>empty(), Optional.<Double>empty());

            double press;
            double seePress;
            double grdPress;
            Pressure pressure = new Pressure(Optional.<Double>empty(), Optional.<Double>empty(),
                    Optional.<Double>empty());

            int humidity = 0;

            double speed;
            double direction;
            Wind wind = new Wind(Optional.<Double>empty(), Optional.<Double>empty());

            int wId;
            String wMain;
            String wDesc;
            String wIcon;
            Weather weather = new Weather(Optional.<Integer>empty(), Optional.<String>empty(),
                    Optional.<String>empty(), Optional.<String>empty());

            String dateTxt;

            if (!jArray.get(i).isJsonNull()) {
                element = jArray.get(i).getAsJsonObject();
                unixDate = getFromJsonLong(element, "dt");
                if (!element.get("main").isJsonNull()) {
                    JsonObject main = element.get("main").getAsJsonObject();

                    temp = getFromJsonDouble(main, "temp");
                    minTemp = getFromJsonDouble(main, "temp_min");
                    maxTemp = getFromJsonDouble(main, "temp_max");
                    temperature = new Temperature(Optional.of(temp),
                            Optional.of(minTemp), Optional.of(maxTemp));

                    press = getFromJsonDouble(main, "pressure");
                    seePress = getFromJsonDouble(main, "sea_level");
                    grdPress = getFromJsonDouble(main, "grnd_level");
                    pressure = new Pressure(Optional.of(press),
                            Optional.of(seePress), Optional.of(grdPress));

                    humidity = getFromJsonInteger(main, "humidity");
                }
                if (!element.get("wind").isJsonNull()) {
                    JsonObject tempWind = element.get("wind").getAsJsonObject();

                    speed = getFromJsonDouble(tempWind, "speed");
                    direction = getFromJsonDouble(tempWind, "deg");
                    wind = new Wind(Optional.of(speed), Optional.of(direction));
                }
                if (!element.get("weather").isJsonNull()) {
                    if (!element.get("weather").getAsJsonArray().get(0).isJsonNull()) {
                        JsonObject tempWeather = element.get("weather")
                                .getAsJsonArray().get(0).getAsJsonObject();

                        wId = getFromJsonInteger(tempWeather, "id");
                        wMain = getFromJsonString(tempWeather, "main");
                        wDesc = getFromJsonString(tempWeather, "description");
                        wIcon = getFromJsonString(tempWeather, "icon");
                        weather = new Weather(Optional.of(wId), Optional.of(wMain),
                                Optional.of(wDesc), Optional.of(wIcon));

                    }
                }

                dateTxt = getFromJsonString(element, "dt_txt");
                // condition
                Condition con = new Condition();
                con.setUnixDate(Optional.of(unixDate));
                con.setTemperature(Optional.of(temperature));
                con.setPressure(Optional.of(pressure));
                con.setHumidity(Optional.of(humidity));
                con.setWind(Optional.of(wind));
                con.setWeather(Optional.of(weather));
                con.setDateTxt(Optional.of(dateTxt));
                // add to array
                conditions.add(con);
            }
        }
        return conditions;
    }
}
