package ru.iruchidesu.weathertest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.iruchidesu.weathertest.TestHelper;
import ru.iruchidesu.weathertest.model.Weather;
import ru.iruchidesu.weathertest.service.WeatherService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.iruchidesu.weathertest.TestHelper.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WeatherService service;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @Test
    void create() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post("/rest/weather")
                .param("startDate", START_DATE_STRING).param("endDate", END_DATE_STRING))
                .andExpect(status().isOk());

        Weather created = TestHelper.readFromJson(action);
        int newId = created.id();
        Weather expect = new Weather(newId, START_DATE, END_DATE, 17.78165771297007);
        assertThat(created).isEqualTo(expect);
    }

    @Test
    void getAll() throws Exception {
        Weather expect = new Weather(1, START_DATE_INIT, END_DATE_INIT, 18.233333333333334);

        perform(MockMvcRequestBuilders.get("/rest/weather"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestHelper.contentJson(expect));
    }

    @Test
    void createInvalid() throws Exception {
        perform(MockMvcRequestBuilders.post("/rest/weather")
                .param("startDate", START_DATE_STRING).param("endDate", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createDateNotFound() throws Exception {
        perform(MockMvcRequestBuilders.post("/rest/weather")
                .param("startDate", START_DATE_STRING).param("endDate", START_DATE_STRING))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString("Data not found")));
    }
}
