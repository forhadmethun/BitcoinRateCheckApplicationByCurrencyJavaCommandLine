import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class BitCoinRateCheckApplicationByCurrencyTest {

    @Test
    public void getCurrencypriceAPIResponseTest() throws IOException {
        BitcoinRateCheckApplicationByCurrency.getCurrencypriceAPIResponse();
        Assert.assertEquals(BitcoinRateCheckApplicationByCurrency.currencyPriceAPIResponse.has("bpi"), true, "Checking if API response is successful and it contains 'bpi' property or not.");
    }

    @Test
    public void currencyCodenotSupportedOrAPIResponseIsNullTest() throws IOException {
        BitcoinRateCheckApplicationByCurrency.getCurrencypriceAPIResponse();
        BitcoinRateCheckApplicationByCurrency.currency = "USD";
        Assert.assertEquals(BitcoinRateCheckApplicationByCurrency.currencyCodenotSupportedOrAPIResponseIsNull(), false, "API response is successful and 'USD' currency code is supported");
    }

    @Test
    public void getInformationFromAPITest() throws IOException {
        BitcoinRateCheckApplicationByCurrency.getCurrencypriceAPIResponse();
        BitcoinRateCheckApplicationByCurrency.currency = "USD";
        BitcoinRateCheckApplicationByCurrency.getInformationFromAPI();
        Assert.assertEquals(BitcoinRateCheckApplicationByCurrency.priceListForDateRange.isEmpty(), false, "Necessary data found to get summary of last 30 days data");
    }

}
