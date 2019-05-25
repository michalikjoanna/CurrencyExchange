package com.kainoss.exchange;

import com.kainoss.exchange.entity.Currency;
import com.kainoss.exchange.repository.CurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CurrencyExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CurrencyRepository currencyRepository) throws IOException {

            List<Currency> codes = new ArrayList<>();
            URL url = new URL("https://openexchangerates.org/api/currencies.json");
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Chrome");

            Scanner scanner = new Scanner(connection.getInputStream());
            List<String> stringJSon = new ArrayList<>();

            while (scanner.hasNextLine()) {
                stringJSon.add(scanner.nextLine());
            }

            stringJSon.remove(0);
            stringJSon.remove(stringJSon.size() - 1);


            for (String code : stringJSon) {
                code = code.trim().substring(1, 4);
                Currency currency = new Currency();
                currency.setCode(code);
                codes.add(currency);
            }

        return (args) -> {
            for (Currency c : codes) {
                currencyRepository.save(c);
            }
        };
    }

}
