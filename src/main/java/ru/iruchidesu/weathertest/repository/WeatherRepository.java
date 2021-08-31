package ru.iruchidesu.weathertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.iruchidesu.weathertest.model.Weather;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    Optional<Weather> getWeatherByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);
}
