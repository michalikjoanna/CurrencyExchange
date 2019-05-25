package com.kainoss.exchange.model;

import com.google.gson.annotations.SerializedName;
import com.kainoss.exchange.model.Rate;
import lombok.Data;

@Data
public class RealtimeCurrency {

    @SerializedName("Realtime Currency Exchange Rate")
    private Rate rate;

}
