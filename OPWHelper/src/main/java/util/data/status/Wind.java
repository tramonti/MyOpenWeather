package util.data.status;

import java.util.Optional;

/**
 * Created by olesia on 06.05.16.
 * It is a POJO generated from Json
 * Wind Object created from JsonObject
 */
public class Wind {
    /*fields*/
    private double speed;
    private double direction;

    /*constructor*/
    public Wind(Optional<Double> speed, Optional<Double> direction) {
        this.speed = speed.orElse(0.0);
        this.direction = direction.orElse(0.0);
    }


    /*methods*/
    /*getters*/

    /**
     * @return double wind speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @return double speed direction
     */
    public double getDirection() {
        return direction;
    }

    /*toString*/
    @Override
    public String toString() {
        return "wind: speed - " + speed + ", direction - " + direction;
    }
}
