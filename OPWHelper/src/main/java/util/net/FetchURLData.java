package util.net;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by olesia on 05.05.16.
 * This class is use json from <href="http://openweathermap.org/city">Open Weather Map</href>
 * it converts json from Open Weather to string and to JsonObject from gson library
 */
public class FetchURLData {
    /*constructor*/
    public FetchURLData() {
    }

    /*methods*/

    /**
     * it uses info from <href="http://openweathermap.org/city">Open Weather Map</href>
     *
     * http://api.openweathermap.org/data/2.5/forecast?id=702550&APPID=713d9a6a9e5940714f0f60d0f56a095d
     *
     * @param apiRequest is an URL, for example
     *                   "http://api.openweathermap.org/data/2.5/forecast/city?id=702550&units=metric&APPID=Key"
     * @return String json object
     * @throws IllegalArgumentException if url address is illegal
     */
    public String createJSON(String apiRequest) throws ConnectException {
        String urlPattern = "http:\\/\\/api\\.openweathermap\\.org\\/data\\/2\\.5\\/forecast\\?id=\\d*&APPID=\\w*&units=metric";
        Pattern p = Pattern.compile(urlPattern);
        Matcher m = p.matcher(apiRequest);
        if(!m.find()){
            throw new IllegalArgumentException("Illegal Api address");
        }

        StringBuilder sb = new StringBuilder();
        // url  "http://api.openweathermap.org/data/2.5/forecast/city?id=702550&units=metric&APPID=713d9a6a9e5940714f0f60d0f56a095d"
        // key = 713d9a6a9e5940714f0f60d0f56a095d
        // Lviv id = 702550
        try {

            URL url = new URL(apiRequest);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp = "";

            while (null != (strTemp = br.readLine())) {
                sb.append(strTemp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ConnectException("InternetConnection failed");
        }
        return sb.toString();
    }

    /**
     * it uses JsonParser to create JsonObject
     *
     * @param json has a String type
     * @return JsonObject
     */
    public JsonObject createJsonObject(String json) {

        JsonParser parser = new JsonParser();
        Object obj = parser.parse(json);
        JsonObject jsonObj = (JsonObject) obj;
        return jsonObj;
    }

}
