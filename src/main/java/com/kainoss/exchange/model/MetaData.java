package com.kainoss.exchange.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class MetaData {

    @SerializedName("1. Information")
    private String information;
    @SerializedName("2. From Symbol")
    private String fromSymbol;
    @SerializedName("3. To Symbol")
    private String toSymbol;
    @SerializedName("4. Last Refreshed")
    private String lastRefreshed;
    @SerializedName("5. Time Zone")
    private String timeZone;
}
