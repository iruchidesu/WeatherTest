package ru.iruchidesu.weathertest.model;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WeatherResponse {
    private Map<String, Double> weather;

    public double getAverageTemperature() {
        return weather.values().stream().mapToDouble(s -> s).average().orElse(0.0);
    }
}
