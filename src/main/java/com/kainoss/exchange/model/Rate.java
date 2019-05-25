package com.kainoss.exchange.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Rate {
    @SerializedName("1. From_Currency Code")
    private String fromCurencyCode;
    @SerializedName("2. From_Currency Name")
    private String fromCurencyName;
    @SerializedName("3. To_Currency Code")
    private String toCurencyCode;
    @SerializedName("4. To_Currency Name")
    private String toCurrencyName;
    @SerializedName("5. Exchange Rate")
    private Double exchangeRate;
    @SerializedName("6. Last Refreshed")
    private String lastRefreshed;
    @SerializedName("7. Time Zone")
    private String timeZone;
    @SerializedName("8. Bid Price")
    private String bidPrice;
    @SerializedName("9. Ask Price")
    private String askPrice;
}
