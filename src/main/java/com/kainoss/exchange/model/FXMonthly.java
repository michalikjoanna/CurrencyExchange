package com.kainoss.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class FXMonthly {

    @SerializedName("Meta Data")
    private MetaData metaData;
    @JsonIgnore
    private TimeSeriesFX timeSeriesFX;


}