package ru.iruchidesu.weathertest.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.iruchidesu.weathertest.model.Weather;
import ru.iruchidesu.weathertest.service.WeatherService;
import ru.iruchidesu.weathertest.util.WeatherUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/weather", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class WeatherController {

    WeatherService service;

    @PostMapping
    public ResponseEntity<Weather> create(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) throws URISyntaxException {
        Weather created = service.getByDates(startDate, endDate);
        if (created == null) {
            created = service.create(getWeatherResponse(startDate, endDate), startDate, endDate);
        }
        return ResponseEntity.ok().body(created);
    }

    private String getWeatherResponse(LocalDate startDate, LocalDate endDate) throws URISyntaxException {
        String strUri = WeatherUtil.generateUri(startDate, endDate);
        URI uri = new URI(strUri);
        RestTemplate template = new RestTemplate();
        RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, uri);
        ResponseEntity<String> weatherResponse = template.exchange(uri, HttpMethod.GET, request, String.class);
        return weatherResponse.getBody();
    }

    @GetMapping
    public List<Weather> getAll() {
        return service.getAll();
    }
}
