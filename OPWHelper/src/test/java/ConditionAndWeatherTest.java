import org.junit.Test;
import util.data.status.Condition;
import util.data.status.Weather;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;

/**
 * Created by olesia on 08.05.16.
 * Testing Condition exceptions
 * Testing Weather exceptions
 */
public class ConditionAndWeatherTest {
    // condition test
    @Test(expected = NumberFormatException.class)
    public void unixDataExceptionTest() {
        Condition con = new Condition();
        con.setUnixDate(Optional.of(new Long(-876656755)));
        con.getUnixDate();
    }

    @Test
    public void conditionParamsTest(){
        Condition condition = new Condition();
        assertNotNull(condition.getDateTxt());
        assertNotNull(condition.getHumidity());
        assertNotNull(condition.getPressure());
        assertNotNull(condition.getTemperature());
        assertNotNull(condition.getUnixDate());
        assertNotNull(condition.getWeather());
        assertNotNull(condition.getWind());
//        System.out.println(condition);

    }




}
