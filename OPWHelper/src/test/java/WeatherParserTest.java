import static org.junit.Assert.*;
import org.junit.Test;
import util.data.city.City;
import util.net.WeatherParser;

import java.net.ConnectException;


/**
 * Created by olesia on 08.05.16.
 */
public class WeatherParserTest {

    @Test
    public void cityObjectTest(){
        WeatherParser wp = new WeatherParser();
        City zoloch = null;
        try {
//            zoloch = wp.getCityObject(686896);
            zoloch = wp.getProtectedCity(686896);
        } catch (ConnectException e) {
            e.printStackTrace();
        }
        assertNotNull(zoloch);
//        System.out.println(zoloch);


    }
}
