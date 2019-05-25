package com.kainoss.exchange.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TimeSeriesFX {

    @SerializedName("months")
    List<Date> rates = new ArrayList<>();



    @Override
    public String toString() {
        return "TimeSeriesFX{" +
                "rates=" + rates +
                '}';
    }
}
