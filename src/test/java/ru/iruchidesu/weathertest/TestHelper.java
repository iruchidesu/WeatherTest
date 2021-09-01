package ru.iruchidesu.weathertest;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.iruchidesu.weathertest.model.Weather;
import ru.iruchidesu.weathertest.util.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHelper {
    public static final String TEST_STRING = "[1202009010000,18.3,1202009010003,18.4,1202009010005,18.5,1202009021056,18.3,1202009021059,17.9,1202009021100,18]";
    public static final LocalDate START_DATE = LocalDate.of(2020, 9, 1);
    public static final LocalDate END_DATE = LocalDate.of(2020, 9, 3);

    public static final LocalDate START_DATE_INIT = LocalDate.of(2020, 8, 31);
    public static final LocalDate END_DATE_INIT = LocalDate.of(2020, 9, 2);

    public static final String START_DATE_STRING = "2020-09-01";
    public static final String END_DATE_STRING = "2020-09-03";

    public static ResultMatcher contentJson(Iterable<Weather> expected) {
        return result -> assertThat(JsonUtil.readValues(getContent(result), Weather.class)).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Weather... expected) {
        return contentJson(List.of(expected));
    }

    public static Weather readFromJson(ResultActions action) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action.andReturn()), Weather.class);
    }

    private static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }
}
