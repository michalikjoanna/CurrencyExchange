package com.kainoss.exchange.service;

import com.kainoss.exchange.converters.RealtimeCurrencyConverter;
import com.kainoss.exchange.dto.CurrencyDto;
import com.kainoss.exchange.entity.Currency;
import com.kainoss.exchange.model.Rate;
import com.kainoss.exchange.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public static Rate getRealtimeCurrencyRate(String fromCurrency, String toCurrency) throws IOException {

        URL url = new URL("https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" +fromCurrency +
                "&to_currency=" + toCurrency + "&apikey=NIDO9L4EQNJYJ94R");
        RealtimeCurrencyConverter realtimeCurrencyConverter = new RealtimeCurrencyConverter(url);

        return realtimeCurrencyConverter.fromJson().get().getRate();
    }

    public void create(CurrencyDto currencyDto) {
        Currency currency = mapTo(currencyDto);
        currencyRepository.save(currency);
    }

    public Optional<Currency> findByID(Long id){

        return currencyRepository.findById(id);
    }

    public List<CurrencyDto> findAll() {
        List<Currency> currencies = currencyRepository.findAll();
        return currencies.stream().map(this::mapTo).collect(Collectors.toList());
    }


    private CurrencyDto mapTo(Currency currency) {
        CurrencyDto dto = new CurrencyDto();
        dto.setId(currency.getId());
        dto.setCode(currency.getCode());
        return dto;
    }

    private Currency mapTo(CurrencyDto dto) {
        Currency currency = new Currency();
        currency.setId(dto.getId());
        currency.setCode(dto.getCode());
        return currency;
    }
}
