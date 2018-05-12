package util.data.city;


import util.data.status.Condition;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by olesia on 05.05.16.
 * City contains all needed json info
 * and has filter methods to filter it's conditions by time and by date
 */
public class City {
    /*fields*/
    private long id;
    private String name;
    private Coordinates location;
    private String country;
    private ArrayList<Condition> conditions;

    /*constructor*/
    public City(Optional<Long> id, Optional<String> name, Optional<Coordinates> location, Optional<String> country, ArrayList<Condition> conditions) {
        this.id = id.orElse((long) 2643743);
        this.name = name.orElse("London");
        this.location = location.orElse(new Coordinates(Optional.<Float>empty(),Optional.<Float>empty()));
        this.country = country.orElse("GB");

        if (conditions != null)
            this.conditions = conditions;
        else this.conditions = new ArrayList<>();
    }

    /*getters*/

    /**
     * @throws NumberFormatException
     */
    public long getId() {
        if (id < 0) throw new NumberFormatException("Illegal id");
        return id;
    }

    /**
     * @throws NullPointerException
     */
    public String getName() {
        if (name == null) throw new NullPointerException("Illegal name");
        return name;
    }

    /**
     * @throws NullPointerException
     */
    public Coordinates getLocation() {
        if (location == null) throw new NullPointerException("Illegal coordinates");
        return location;
    }

    /**
     * @throws NullPointerException
     */
    public String getCountry() {
        if (country == null) throw new NullPointerException("Illegal country");
        return country;
    }

    /**
     * @throws NullPointerException
     */
    public ArrayList<Condition> getConditions() {
        if (conditions == null) throw new NullPointerException("Illegal conditions");
        return conditions;
    }

    /*methods*/

    /**
     * This is a filter method which filters Conditions by date
     *
     * @param time LocalDateTime
     * @return ArrayList of Condition
     */
    public ArrayList<Condition> getConditionsByDate(LocalDateTime time) {

        ArrayList<Condition> result = new ArrayList<>();
        String pattern = time.toString().substring(0, 10);
        Pattern p = Pattern.compile(pattern);
        for (Condition c : getConditions()) {
            Matcher m = p.matcher(c.toString());
            if (m.find()) {
                result.add(c);
            }
        }
        return result;
    }

    /**
     * This is a filter method which allows
     * to get one Condition object using it's time
     *
     * @param time LocalDateTime
     * @return Condition object from all objects
     */
    public Condition getConditionByTime(LocalDateTime time) {
        Condition con = new Condition();
        String date = time.toString().substring(0, 10);
        String hour;
        if (time.getHour() > 0 && time.getHour() <= 3) {
            hour = "03:00:00";
        } else if (time.getHour() > 3 && time.getHour() <= 6) {
            hour = "06:00:00";
        } else if (time.getHour() > 6 && time.getHour() <= 9) {
            hour = "09:00:00";
        } else if (time.getHour() > 9 && time.getHour() <= 12) {
            hour = "12:00:00";
        } else if (time.getHour() > 12 && time.getHour() <= 15) {
            hour = "15:00:00";
        } else if (time.getHour() > 15 && time.getHour() <= 18) {
            hour = "18:00:00";
        } else if (time.getHour() > 18 && time.getHour() <= 21) {
            hour = "21:00:00";
        } else if (time.getHour() > 21 && time.getHour() <= 24) {
            hour = "00:00:00";
        } else {
            hour = "00:00:00";
        }
        String pattern = date + " " + hour;
        for (Condition c : getConditions()) {
            if (c.getDateTxt().equals(pattern)) {
                con = c;
            }
        }
        return con;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + id + ", name: " + name + ", " + location + ", country: " + country + "\n");
        sb.append("list of conditions:\n");
        for (Condition c : conditions) {
            sb.append(c + "\n");
        }
        return sb.toString();
    }
}

