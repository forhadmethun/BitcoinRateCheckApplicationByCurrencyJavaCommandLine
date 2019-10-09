import org.json.JSONObject;
import util.APIUtil;
import util.ListUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

public class BitcoinRateCheckApplicationByCurrency {

    public static final String currencyPriceAPIGetURL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    public static JSONObject currencyPriceAPIResponse = null;

    public static String currency = null;

    public static Double bitcoinRateInRequestedCurrency = null;

    public static final String dateFormat = "yyyy-MM-dd";

    public static final int forHowManyLastDayWeWantToSeeData = 30;

    public static ArrayList<Double> priceListForDateRange = null;


    public static void main(String[] args) throws IOException {
        displayInformationFromAPI();
    }

    static void displayInformationFromAPI() throws IOException {

        getCurrencypriceAPIResponse();
        while(true){

            requestUserForInput();

            if(currencyCodenotSupported()){
                out.println("Something went wrong or the currency code is not supported by the API. try(USD, EUR, GBP, etc.)");
                continue;
            }
            getInformationFromAPI();

            showInformation();
        }
    }
    static void getCurrencypriceAPIResponse() throws IOException {
        currencyPriceAPIResponse = APIUtil.getJSONAPIdataFromURL(currencyPriceAPIGetURL);
    }
    static void requestUserForInput() throws IOException {
        out.println("Input a currency code (USD, EUR, GBP, etc.)");
        Scanner scanner = new Scanner(System.in);
        currency = scanner.nextLine();

    }

    public static boolean currencyCodenotSupported(){
        if(currencyPriceAPIResponse == null) return true;
        if(currencyPriceAPIResponse.get("bpi") == null) return true;
        if(!((JSONObject) currencyPriceAPIResponse.get("bpi")).has(currency)) return true;

        return false;
    }

    public static void getInformationFromAPI() throws IOException {


        JSONObject bpiObject = (JSONObject) currencyPriceAPIResponse.get("bpi");
        JSONObject currencyDataObject = (JSONObject) bpiObject.get(currency);


        bitcoinRateInRequestedCurrency = (Double) currencyDataObject.get("rate_float");
        String apiDataRangeURL = urlBuilderToGetLast30DaysInformation();
        JSONObject lastOneMonthDataObject = APIUtil.getJSONAPIdataFromURL(apiDataRangeURL);
        JSONObject priceObjectForLastmonth = (JSONObject) lastOneMonthDataObject.get("bpi");
        Set<String> dateSet = priceObjectForLastmonth.keySet();
        priceListForDateRange = new ArrayList<Double>();
        for (String date : dateSet) {
            double price = (Double) priceObjectForLastmonth.get(date);
            priceListForDateRange.add(price);
        }
    }

    public static String urlBuilderToGetLast30DaysInformation(){
        Date fromday = getNdaysBeforeDataFromCurrentDate(forHowManyLastDayWeWantToSeeData);
        Date today = new Date();

        String currentDate = getDateInSpecificFormat(today,dateFormat);
        String fromDate = getDateInSpecificFormat(fromday, dateFormat);

        String apiDataRangeURL = "https://api.coindesk.com/v1/bpi/historical/close.json?start=" + fromDate + "&end=" + currentDate + "&?currency=" + currency;

        return apiDataRangeURL;
    }

    public static Date getNdaysBeforeDataFromCurrentDate(int n){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -n);
        Date dateBefore30Days = cal.getTime();
        return dateBefore30Days;
    }

    public static String getDateInSpecificFormat(Date date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static void showInformation(){
        out.println("- The current Bitcoin rate, in the requested currency: " + bitcoinRateInRequestedCurrency);
        out.println("- The lowest Bitcoin rate in the last 30 days, in the requested currency: " + ListUtil.getMinValue(priceListForDateRange));
        out.println("- The highest Bitcoin rate in the last 30 days, in the requested currency: " + ListUtil.getMaxValue(priceListForDateRange));
    }

}
