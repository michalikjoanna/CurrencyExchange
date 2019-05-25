package com.kainoss.exchange.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Date {

    @SerializedName("1. open")
    private String open;
    @SerializedName("2. high")
    private String high;
    @SerializedName("3. low")
    private String low;
    @SerializedName("4. close")
    private String close;
    private String date;
    private Double avg;

    public Double getAvg() {
        return (Double.valueOf(open) + Double.valueOf(high) + Double.valueOf(low) + Double.valueOf(close))/4;
    }
}
