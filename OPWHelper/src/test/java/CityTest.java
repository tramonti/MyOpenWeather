import static org.junit.Assert.*;

import org.junit.Test;
import util.data.city.City;
import util.data.city.Coordinates;
import util.data.status.Condition;
import util.net.WeatherParser;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by olesia on 08.05.16.
 */
public class CityTest {

    // method tests
    @Test
    public void conditionByLocalTimeTest() {
        WeatherParser wp = new WeatherParser();
        City lviv = null;
        try {
            lviv = wp.getProtectedCity(702550);
        } catch (ConnectException e) {
            e.printStackTrace();
        }

        LocalDateTime time = LocalDateTime.now();
        Condition c = lviv.getConditionByTime(time);

        assertNotNull(c);

        String tmPattern = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Pattern p = Pattern.compile(tmPattern);
        Matcher m = p.matcher(c.getDateTxt());
        assertTrue(m.find());

    }

    @Test
    public void conditionsByDateTest() {
        WeatherParser wp = new WeatherParser();
        City lviv = null;
        try {
            lviv = wp.getProtectedCity(702550);
        } catch (ConnectException e) {
            e.printStackTrace();
        }

        LocalDateTime time = LocalDateTime.now();

        ArrayList<Condition> cons = lviv.getConditionsByDate(time);

        assertNotNull(cons);
        String tmPattern = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Pattern p = Pattern.compile(tmPattern);
        for (Condition c : cons) {
            Matcher m = p.matcher(c.getDateTxt());
            assertTrue(m.find());
        }
    }

    @Test
    public void coordinatesTest() {

        Coordinates coordinates = new Coordinates(Optional.<Float>empty(),
                Optional.<Float>empty());
        assertNotNull(coordinates.getLatitude());
        assertNotNull(coordinates.getLongitude());
        assertTrue(coordinates.getLatitude() == 0 && coordinates
                .getLongitude() == 0);


    }
    @Test
    public void cityParamsTest(){
        City city = new City(Optional.<Long>empty(),Optional.<String>empty(),
                Optional.<Coordinates>empty(),Optional.<String>empty(),
                null);
        assertNotNull(city.getId());
        assertNotNull(city.getName());
        assertNotNull(city.getCountry());
        assertNotNull(city.getLocation());
        assertNotNull(city.getConditions());

//        System.out.println(city);

    }
}
