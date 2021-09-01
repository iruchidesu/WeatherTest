package ru.iruchidesu.weathertest.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.test.context.jdbc.Sql;
import ru.iruchidesu.weathertest.NotFoundException;
import ru.iruchidesu.weathertest.model.Weather;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.iruchidesu.weathertest.TestHelper.*;

@SpringBootTest
@Sql(scripts = "classpath:data.sql")
class WeatherServiceTest {

    @Autowired
    private WeatherService service;

    @Test
    void create() {
        Weather created = service.create(TEST_STRING, START_DATE, END_DATE);
        int newId = created.id();
        Weather expect = new Weather(newId, START_DATE, END_DATE, 18.2333333333333);
        assertThat(created).isEqualTo(expect);
        assertThat(service.getByDates(START_DATE, END_DATE)).isEqualTo(expect);
    }

    @Test
    void createDateNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                Runnable runnable = () -> service.create(TEST_STRING, null, null);
                runnable.run();
            } catch (Exception e) {
                Throwable rootCause = NestedExceptionUtils.getRootCause(e);
                throw rootCause != null ? rootCause : e;
            }
        });
    }

    @Test
    void createStringEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                Runnable runnable = () -> service.create("", START_DATE, END_DATE);
                runnable.run();
            } catch (Exception e) {
                Throwable rootCause = NestedExceptionUtils.getRootCause(e);
                throw rootCause != null ? rootCause : e;
            }
        });
    }

    @Test
    void createDataNotFound() {
        assertThrows(NotFoundException.class, () -> {
            try {
                Runnable runnable = () -> service.create("[]", START_DATE, START_DATE);
                runnable.run();
            } catch (Exception e) {
                Throwable rootCause = NestedExceptionUtils.getRootCause(e);
                throw rootCause != null ? rootCause : e;
            }
        });
    }

    @Test
    void getAll() {
        Weather expect = new Weather(1, START_DATE_INIT, END_DATE_INIT, 18.2333333333333);
        List<Weather> expectList = List.of(expect);
        assertThat(service.getAll()).isEqualTo(expectList);
    }

    @Test
    void getByDates() {
        Weather expect = new Weather(1, START_DATE_INIT, END_DATE_INIT, 18.2333333333333);
        assertThat(service.getByDates(START_DATE_INIT, END_DATE_INIT)).isEqualTo(expect);
    }

    @Test
    void getByDatesNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                Runnable runnable = () -> service.getByDates(null, null);
                runnable.run();
            } catch (Exception e) {
                Throwable rootCause = NestedExceptionUtils.getRootCause(e);
                throw rootCause != null ? rootCause : e;
            }
        });
    }

    @Test
    void getByDatesNotFound() {
        assertThat(service.getByDates(START_DATE.minusDays(1), END_DATE)).isNull();
    }
}
