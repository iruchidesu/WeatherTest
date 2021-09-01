package ru.iruchidesu.weathertest.util;

import lombok.experimental.UtilityClass;
import ru.iruchidesu.weathertest.model.WeatherResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@UtilityClass
public class WeatherUtil {
    public static WeatherResponse getWeatherResponse(String weather) {
        weather = weather.replaceAll("(.{14})[.*?,]", "$1:");
        weather = weather.replace("[", "{");
        weather = weather.replace("]", "}");
        Map<String, Double> map = JsonUtil.readValueMap(weather);
        return new WeatherResponse(map);
    }

    public static String generateUri(LocalDate startDate, LocalDate endDate) {
        StringBuilder stringBuilder = new StringBuilder("http://pogoda.atpm-air.ru/data.aspx?action=temperature&dat1=");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E+LLL+dd+yyyy").withLocale(Locale.ENGLISH);
        stringBuilder.append(startDate.format(formatter));
        stringBuilder.append("&dat2=");
        stringBuilder.append(endDate.format(formatter)).append("&comparison=0");
        return stringBuilder.toString();
    }
}
