package com.kainoss.exchange.controller;

import com.kainoss.exchange.converters.TimeSeriesFXConverter;
import com.kainoss.exchange.dto.CurrencyDto;
import com.kainoss.exchange.entity.Currency;
import com.kainoss.exchange.model.Date;
import com.kainoss.exchange.model.TimeSeriesFX;
import com.kainoss.exchange.service.CurrencyService;
import com.kainoss.exchange.service.FXMontlhyServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"","/","/index","/index.html"})
public class IndexController {

    CurrencyService currencyService;

    public IndexController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) throws IOException {
        List<CurrencyDto> codes = currencyService.findAll();
        model.addAttribute("currencies", codes);
        model.addAttribute("code", new CurrencyDto());
        List<TimeSeriesFX> rates = new ArrayList<>();
        if(request.getSession().isNew()){
            request.getSession().setAttribute("fromCurrency", "USD");
            request.getSession().setAttribute("toCurrency","PLN");
            Double exchangeRate =  CurrencyService.getRealtimeCurrencyRate("USD", "PLN").getExchangeRate();
            request.getSession().setAttribute("exchangeRate", exchangeRate);
            rates = createAFile("USD","PLN");
            model.addAttribute("toCurrency", "USD");
            model.addAttribute("fromCurrency","PLN");
        } else {
            rates = (List<TimeSeriesFX>) request.getSession().getAttribute("rates");
            model.addAttribute("toCurrency", request.getSession().getAttribute("toCurrency"));
            model.addAttribute("fromCurrency", request.getSession().getAttribute("fromCurrency"));
        }

        drawAGraph(rates, model);



        return "index";
    }

    public void drawAGraph(List<TimeSeriesFX> rates, Model model){
        TimeSeriesFX timeSeriesFX = new TimeSeriesFX();
        timeSeriesFX.setRates(rates.get(0).getRates());
        String [] dates = new String [timeSeriesFX.getRates().size()];
        Double [] avg = new Double [timeSeriesFX.getRates().size()];

        int i =0;
        for (Date d : timeSeriesFX.getRates()){
            dates[i] = d.getDate();
            avg[i] = d.getAvg();
            i++;
        }

        model.addAttribute("dates", dates);
        model.addAttribute("avg", avg);
    }

    public List<TimeSeriesFX> createAFile(String fromCurrencyCode, String toCurrencyCode) throws IOException {

        //tworzę plik do pobrania
        final String ratesName = "rates.json";
        URL url = new URL("https://www.alphavantage.co/query?function=FX_MONTHLY&from_symbol="+ fromCurrencyCode
                +"&to_symbol=" + toCurrencyCode + "&apikey=NIDO9L4EQNJYJ94R");

        List<String> jsonString = FXMontlhyServer.createJsonString(url);
        List<String> date = FXMontlhyServer.getDates(jsonString);
        FXMontlhyServer.createAJsonFile(jsonString);
        TimeSeriesFXConverter timeSeriesFXConverter = new TimeSeriesFXConverter(url);
        List<TimeSeriesFX> rates = new ArrayList<>();
        timeSeriesFXConverter.fromJsonFile(ratesName).ifPresent(rates::add);

        for(int i = 0 ; i < date.size(); i++){
            rates.get(0).getRates().get(i).setDate(date.get(i));
        }

       return rates;
    }

    @RequestMapping( method = RequestMethod.POST)
    public String fromCurrencyCode(@Valid CurrencyDto currency,Model model, HttpServletRequest request) throws IOException {

        Optional<Currency> fromCurrency = currencyService.findByID(currency.getId());
        fromCurrency.get().setToCode(currency.getToCode());
        String fromCurrencyCode = fromCurrency.get().getCode();
        String toCurrencyCode = fromCurrency.get().getToCode();

        request.getSession().setAttribute("fromCurrency", fromCurrencyCode);
        request.getSession().setAttribute("toCurrency",toCurrencyCode);

        List<TimeSeriesFX> rates = createAFile(fromCurrencyCode,toCurrencyCode);
        drawAGraph(rates, model);


        request.getSession().setAttribute("rates",rates);
        Double exchangeRate =  CurrencyService.getRealtimeCurrencyRate(fromCurrency.get().getCode(),fromCurrency.get().getToCode()).getExchangeRate();
        request.getSession().setAttribute("exchangeRate", exchangeRate);

        return "redirect:index";
    }





}
