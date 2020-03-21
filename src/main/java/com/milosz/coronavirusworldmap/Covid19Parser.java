package com.milosz.coronavirusworldmap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class Covid19Parser {

    private static final String URL =
            "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    public List<Point> getCovidData() throws IOException {

        List<Point> points = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String values = restTemplate.getForObject(URL, String.class);

        StringReader stringReader = new StringReader(values);
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);

        for (CSVRecord strings : parser) {

            double lat = Double.parseDouble(strings.get("Lat"));
            double lon = Double.parseDouble(strings.get("Long"));
            String text = strings.get("3/21/20");
            points.add(new Point(lat, lon, text));
        }

        return points;
    }
}
