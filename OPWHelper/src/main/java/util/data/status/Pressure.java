package util.data.status;

import java.util.Optional;

/**
 * Created by olesia on 06.05.16.
 * It is a POJO generated from Json
 */
public class Pressure {
    /*fields*/
    private double pressure;
    private double seaLevel;
    private double groundLevel;

    /*constructors*/
    public Pressure(Optional<Double> pressure, Optional<Double> seaLevel,
                    Optional<Double> groundLevel) {
        this.pressure = pressure.orElse(0.0);
        this.seaLevel = seaLevel.orElse(0.0);
        this.groundLevel = groundLevel.orElse(0.0);
    }

    /*getters*/

    /**
     * @return double average pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * @return double sea level pressure
     */
    public double getSeaLevel() {
        return seaLevel;
    }

    /**
     * @return double ground level pressure
     */
    public double getGroundLevel() {
        return groundLevel;
    }

    /*toString*/
    @Override
    public String toString() {
        return "pressure - " + pressure;
    }
}
