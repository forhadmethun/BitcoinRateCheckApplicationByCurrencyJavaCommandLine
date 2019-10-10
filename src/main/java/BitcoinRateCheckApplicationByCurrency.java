import org.json.JSONObject;
import util.APIUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;

public class BitcoinRateCheckApplicationByCurrency {

    public static final String currencyPriceAPIGetURL = "https://api.coindesk.com/v1/bpi/currentprice.json";
    public static JSONObject currencyPriceAPIResponse = null;
    public static String currency = null;
    public static Double bitcoinRateInRequestedCurrency = null;
    public static List<Double> priceListForDateRange = null;
    public static Double maxPriceInLasty30Days = null;
    public static Double minPriceInLasty30Days = null;


    public static void main(String[] args) throws IOException {
        displayInformationFromAPI();
    }

    static void displayInformationFromAPI() throws IOException {

        getCurrencypriceAPIResponse();
        while (true) {

            requestUserForInput();

            if (currencyCodenotSupportedOrAPIResponseIsNull()) {
                System.out.println("Something went wrong or the currency code is not supported by the API. try(USD, EUR, GBP, etc.)");
                continue;
            }
            getInformationFromAPI();

            showInformation();
        }
    }

    public static void getCurrencypriceAPIResponse() throws IOException {
        currencyPriceAPIResponse = APIUtil.getJSONAPIdataFromURL(currencyPriceAPIGetURL);
    }

    public static void requestUserForInput() throws IOException {
        System.out.println("Input a currency code (USD, EUR, GBP, etc.)");
        Scanner scanner = new Scanner(System.in);
        currency = scanner.nextLine();

    }

    public static boolean currencyCodenotSupportedOrAPIResponseIsNull() {
        if (checkIfCurrencyPriceApiResponseIsNull()) return true;
        if (!((JSONObject) currencyPriceAPIResponse.get("bpi")).has(currency)) return true;
        return false;
    }

    static boolean checkIfCurrencyPriceApiResponseIsNull() {
        if (currencyPriceAPIResponse == null) return true;
        if (currencyPriceAPIResponse.get("bpi") == null) return true;
        return false;
    }

    static void getInformationFromAPI() throws IOException {
        parsePriceListFromJsonData();
        DoubleSummaryStatistics statistics = priceListForDateRange.stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();
        maxPriceInLasty30Days = statistics.getMax();
        minPriceInLasty30Days = statistics.getMin();

    }
    static void parsePriceListFromJsonData() throws IOException {
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

    static String urlBuilderToGetLast30DaysInformation() {

        DateTimeFormatter isoLocalDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

        LocalDate dNow = LocalDate.now();
        LocalDate dateBefore30Days = dNow.minusDays(30);

        String currentDate = isoLocalDateFormatter.format(dNow);
        String fromDate = isoLocalDateFormatter.format(dateBefore30Days);

        String apiDataRangeURL = "https://api.coindesk.com/v1/bpi/historical/close.json?start=" + fromDate + "&end=" + currentDate + "&?currency=" + currency;

        return apiDataRangeURL;
    }

    static void showInformation() {
        System.out.println("- The current Bitcoin rate, in the requested currency: " + bitcoinRateInRequestedCurrency);
        System.out.println("- The lowest Bitcoin rate in the last 30 days, in the requested currency: " + minPriceInLasty30Days);
        System.out.println("- The highest Bitcoin rate in the last 30 days, in the requested currency: " + maxPriceInLasty30Days);

    }


}
