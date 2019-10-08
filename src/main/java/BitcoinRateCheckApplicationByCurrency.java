import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

public class BitcoinRateCheckApplicationByCurrency {
    public static final String URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
    public static JSONObject jsonAPIresponseObject = null;

    public static void main(String[] args) throws IOException {
        jsonAPIresponseObject = getJSONAPIdataFromURL(URL);
        out.println("Input a currency code (USD, EUR, GBP, etc.)");
        ;
        Scanner scanner = new Scanner(System.in);
        String currency = scanner.nextLine();

        JSONObject bpiObject = (JSONObject) jsonAPIresponseObject.get("bpi");

        JSONObject currencyDataObject = (JSONObject) bpiObject.get(currency);

        Double currentRate = (Double) currencyDataObject.get("rate_float");

        Date dNow = new Date();

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Current Date: " + ft.format(dNow));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -30);
        Date dateBefore30Days = cal.getTime();


        String currentDate = ft.format(dNow);
        String fromDate = ft.format(dateBefore30Days);

        String apiDataRangeURL = "https://api.coindesk.com/v1/bpi/historical/close.json?start=" + fromDate + "&end=" + currentDate + "&?currency=" + currency;
        JSONObject lastOneMonthDataObject = getJSONAPIdataFromURL(apiDataRangeURL);

        JSONObject obj = (JSONObject) lastOneMonthDataObject.get("bpi");

        Set<String> keySet = obj.keySet();
        ArrayList<Double> priceListForDateRange = new ArrayList<Double>();
        for (String x : keySet) {
            double price = (Double) obj.get(x);
            priceListForDateRange.add(price);
        }


        out.println("- The current Bitcoin rate, in the requested currency: " + currentRate);
        out.println("- The lowest Bitcoin rate in the last 30 days, in the requested currency: " + getMinValue(priceListForDateRange));
        out.println("- The highest Bitcoin rate in the last 30 days, in the requested currency: " + getMaxValue(priceListForDateRange));


    }

    public static JSONObject getJSONAPIdataFromURL(String url) throws IOException {
        JSONObject responseJSON = null;
        URL urlForGetRequest = new URL(url);
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
//        conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
//            System.out.println("JSON String Result " + response.toString());
            //GetAndPost.POSTRequest(response.toString());
            responseJSON = new JSONObject(response.toString());
        } else {
            System.out.println("GET NOT WORKED");
        }
        return responseJSON;
    }

    public static double getMaxValue(ArrayList<Double> numbers) {

        double maxValue = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) > maxValue) {
                maxValue = numbers.get(i);
            }
        }
        return maxValue;
    }

    public static double getMinValue(ArrayList<Double> numbers) {
        double minValue = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) < minValue) {
                minValue = numbers.get(i);
            }
        }
        return minValue;
    }
}
