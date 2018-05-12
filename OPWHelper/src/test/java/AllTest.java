import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by olesia on 08.05.16.
 */


@RunWith(Suite.class)
@Suite.SuiteClasses({CityTest.class, FetchUrlDataTest.class,
        WeatherParserTest.class, ConditionAndWeatherTest.class})
public class AllTest {

}
