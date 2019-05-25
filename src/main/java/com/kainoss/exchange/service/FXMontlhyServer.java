package com.kainoss.exchange.service;

import lombok.Data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class FXMontlhyServer {

    public static void createAJsonFile(List<String> jsonString){

            List<String> date = getDates(jsonString);

            String[] clean = new String[jsonString.size()];
            jsonString.toArray(clean);

            if(clean.length != 0) {

                for (int i = 0; i < clean.length; i++) {

                    for (int j = 0; j < date.size(); j++) {

                        if (clean[i].contains(date.get(j))) {
                            clean[i] = jsonString.get(i).replace(date.get(j), "");
                            clean[i] = clean[i].replaceAll("\"\":", "");

                        }
                    }
                }

                try (FileWriter writer = new FileWriter("rates.json");
                     BufferedWriter bw = new BufferedWriter(writer)) {

                    int i = 0;
                    int size = clean.length;
                    bw.write("{");
                    bw.write("\"months\": [");

                    for (String s : clean) {
                        i++;

                        if (i == size - 1) {
                            bw.write("]");
                        } else {
                            bw.write(s);
                            bw.write("\n");

                        }
                    }

                } catch (IOException e) {
                    System.err.format("IOException: %s%n", e);
                }
            }

    }

    public static List<String> getDates(List<String> jsonString){
        List<String> dates = new ArrayList<>();

        new ArrayList<>();
        for(String s: jsonString){
            String regex = "(\\d{4}-\\d{2}-\\d{2})";
            Matcher m = Pattern.compile(regex).matcher(s);
            if (m.find()) {
                dates.add(m.group());
            }


        }
        return dates;
    }

    public static List<String> createJsonString (URL url) throws IOException {
        List<String> jsonString = new ArrayList<>();
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Chrome");

        Scanner scanner = new Scanner(connection.getInputStream());

        boolean timeSeries = false;
        while (scanner.hasNextLine()) {

            if (!timeSeries) {
                if (scanner.nextLine().contains("Time Series FX (Monthly)")) {
                    timeSeries = true;
                }
            }
            if (timeSeries) {
                jsonString.add(scanner.nextLine());
            }
        }

        return jsonString;
    }


}
