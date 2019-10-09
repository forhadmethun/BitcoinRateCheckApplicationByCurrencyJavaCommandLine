package util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIUtil {
    public static JSONObject getJSONAPIdataFromURL(String url) throws IOException {

        JSONObject responseJSON = null;
        URL urlForGetRequest = new URL(url);
        String readLine = null;

        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        int responseCode = conection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            responseJSON = new JSONObject(response.toString());
        } else {
            System.out.println("GET NOT WORKED");
        }

        return responseJSON;
    }
}
