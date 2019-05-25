package com.kainoss.exchange.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Optional;

public abstract class JsonConverter<T> {

    private final URL url;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public JsonConverter(URL jsonURL) {
        this.url = jsonURL;
    }

    public Optional<T> fromJson() {
        try {
            InputStreamReader reader = new InputStreamReader(url.openStream());
            return Optional.of(gson.fromJson(reader, type));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<T> fromJsonFile(String jsonFilename){

        try (FileReader fileReader = new FileReader(jsonFilename)) {
            return Optional.of(gson.fromJson(fileReader, type));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
