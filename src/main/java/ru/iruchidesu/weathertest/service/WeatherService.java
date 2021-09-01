package ru.iruchidesu.weathertest.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.iruchidesu.weathertest.model.Weather;
import ru.iruchidesu.weathertest.model.WeatherResponse;
import ru.iruchidesu.weathertest.repository.WeatherRepository;
import ru.iruchidesu.weathertest.util.WeatherUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class WeatherService {

    WeatherRepository repository;

    public Weather create(String weatherString, LocalDate startDate, LocalDate endDate) {
        Assert.hasLength(weatherString, "weatherString must not be empty");
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate must not be null");
        WeatherResponse created = WeatherUtil.getWeatherResponse(weatherString);
        Weather weather = new Weather(null, startDate, endDate, created.getAverageTemperature());
        return repository.save(weather);
    }

    public List<Weather> getAll() {
        return repository.findAll();
    }

    public Weather getByDates(LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate must not be null");
        return repository.getWeatherByStartDateAndEndDate(startDate, endDate).orElse(null);
    }
}
