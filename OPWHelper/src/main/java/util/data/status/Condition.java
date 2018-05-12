package util.data.status;

import java.util.Optional;

/**
 * Created by olesia on 06.05.16.
 * This Object contains all the needed information about
 * date, temperature, pressure, humidity and wind
 */
public class Condition {
    /*fields*/
    private Optional<Long> unixDate;
    private Optional<Temperature> temperature;
    private Optional<Pressure> pressure;
    private Optional<Integer> humidity;
    private Optional<Wind> wind;
    private Optional<Weather> weather;
    private Optional<String> dateTxt;

    /*constructors*/
    public Condition() {
        this.unixDate = Optional.<Long>empty();
        this.temperature = Optional.<Temperature>empty();
        this.pressure = Optional.<Pressure>empty();
        this.humidity = Optional.<Integer>empty();
        this.wind = Optional.<Wind>empty();
        this.weather = Optional.<Weather>empty();
        this.dateTxt = Optional.<String>empty();
    }

    /*getters and setters*/

    /**
     * @return long unix date
     * @throws NumberFormatException
     */
    public long getUnixDate() {
        if (unixDate.orElse((long) 0) < 0) throw new NumberFormatException("unix date cannot be less than 0");

//        TODO default unix date
        return unixDate.orElse((long) 0);
    }

    /**
     * @param unixDate long type unix date
     */
    public void setUnixDate(Optional<Long> unixDate) {
        this.unixDate = unixDate;
    }

    /**
     * @return Temperature object
     * @throws NullPointerException
     */
    public Temperature getTemperature() {


        return temperature.orElse(new Temperature(Optional.<Double>empty(), Optional.<Double>empty(),
                Optional.<Double>empty()));
    }

    /**
     * @param temperature is Temperature type
     */
    public void setTemperature(Optional<Temperature> temperature) {
        this.temperature = temperature;
    }

    /**
     * @return Pressure object
     * @throws NullPointerException
     */
    public Pressure getPressure() {

        return pressure.orElse(new Pressure(Optional.<Double>empty(), Optional.<Double>empty(),
                Optional.<Double>empty()));
    }

    /**
     * @param pressure has Pressure type
     */
    public void setPressure(Optional<Pressure> pressure) {
        this.pressure = pressure;
    }

    /**
     * @return int humidity in %
     * @throws NumberFormatException
     */
    public int getHumidity() {
        if (humidity.orElse(0) < 0) throw new NumberFormatException("humidity cannot be less than 0");
        return humidity.orElse(0);
    }

    /**
     * @param humidity int humidity %
     */
    public void setHumidity(Optional<Integer> humidity) {
        this.humidity = humidity;
    }

    /**
     * @return Wind object
     */
    public Wind getWind() {

        return wind.orElse(new Wind(Optional.<Double>empty(), Optional.<Double>empty()));
    }

    /**
     * @param wind is Wind object
     */
    public void setWind(Optional<Wind> wind) {
        this.wind = wind;
    }

    /**
     * @return Weather object
     */
    public Weather getWeather() {
        return weather.orElse(new Weather(Optional.<Integer>empty(), Optional.<String>empty(),
                Optional.<String>empty(), Optional.<String>empty()));
    }

    /**
     * @param weather is a Weather object
     */
    public void setWeather(Optional<Weather> weather) {
        this.weather = weather;
    }

    /**
     * @return String date in text format yyyy-mm-dd hh:mm:ss
     */
    public String getDateTxt() {

        //        TODO set default date
        return dateTxt.orElse("2016-5-25 03:00:00");
    }

    /**
     * @param dateTxt String date in text format yyyy-mm-dd hh:mm:ss
     */
    public void setDateTxt(Optional<String> dateTxt) {


        this.dateTxt = dateTxt;
    }

    /*toString*/
    @Override
    public String toString() {
        return "unix date - " + getUnixDate() + ", "
                + getTemperature() + ", "
                + getPressure() + ", humidity - "
                + getHumidity() + "%, "
                + getWeather() + ", "
                + getWind() + " dt_txt - "
                + getDateTxt();
    }
}
