package com.kainoss.exchange.converters;

import com.kainoss.exchange.model.RealtimeCurrency;

import java.net.URL;

public class RealtimeCurrencyConverter extends JsonConverter<RealtimeCurrency> {

    public RealtimeCurrencyConverter(URL jsonURL) {
        super(jsonURL);
    }
}
