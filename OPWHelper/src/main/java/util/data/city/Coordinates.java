package util.data.city;

import java.util.Optional;

/**
 * Created by olesia on 05.05.16.
 * It is a POJO generated from Json
 */
public class Coordinates {
    /*fields*/
    private float longitude;
    private float latitude;

    /*constructors*/

    /**
     * @param latitude  Optional float type
     * @param longitude Optional float type
     *                  used to escape null pointers
     */
    public Coordinates(Optional<Float> longitude, Optional<Float> latitude) {
        this.longitude = longitude.orElse((float) 0);
        this.latitude = latitude.orElse((float) 0);
//        this.latitude = latitude.get();
    }

    /*getters*/

    /**
     * @return float longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @return float latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /*toString*/
    @Override
    public String toString() {
        return "Location is " + longitude + ", " + latitude;
    }
}