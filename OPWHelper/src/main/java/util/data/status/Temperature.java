package util.data.status;

import java.util.Optional;

/**
 * Created by olesia on 06.05.16.
 * It is a POJO generated from Json
 */
public class Temperature {
    /*fields*/
    private double temp;
    private double minTemp;
    private double maxTemp;

    /*constructors*/
    public Temperature(Optional<Double> temp, Optional<Double> minTemp,
                       Optional<Double> maxTemp) {
        this.temp = temp.orElse(0.0);
        this.minTemp = minTemp.orElse(0.0);
        this.maxTemp = maxTemp.orElse(0.0);
    }

    /*getters*/

    /**
     * @return double average temperature
     */
    public double getTemp() {
        return temp;
    }

    /**
     * @return double minimal temperature
     */
    public double getMinTemp() {
        return minTemp;
    }

    /**
     * @return double maximal temperature
     */
    public double getMaxTemp() {
        return maxTemp;
    }

    /*toString*/
    @Override
    public String toString() {
        return "temperature - " + temp + ", max temp - " + maxTemp + ", min temp - " + minTemp;
    }
}
