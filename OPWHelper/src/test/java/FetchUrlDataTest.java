import org.junit.Test;
import util.net.FetchURLData;

import java.net.ConnectException;
import java.net.UnknownHostException;

/**
 * Created by olesia on 08.05.16.
 */
public class FetchUrlDataTest {

    @Test(expected = IllegalArgumentException.class)
    public void createJSONTest() throws UnknownHostException {
        FetchURLData fud = new FetchURLData();
        try {
            fud.createJSON("dsffsa");
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }

    /**run this test if you are offline*/
//    @Test(expected = IllegalAccessError.class)
//    public void connectionCheckTest(){
//        FetchURLData fud = new FetchURLData();
//        try {
//            fud.createJSON("http://api.openweathermap.org/data/2.5/forecast/city?id=702550&units=metric&APPID=713d9a6a9e5940714f0f60d0f56a095d");
//        } catch (ConnectException e) {
//            throw new IllegalAccessError("No Internet");
//        }
//    }

}
