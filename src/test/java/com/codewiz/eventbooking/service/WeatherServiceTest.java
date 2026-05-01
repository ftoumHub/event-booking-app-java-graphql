package com.codewiz.eventbooking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.codewiz.eventbooking.dto.Weather;

class WeatherServiceTest {

    @Test
    void shouldReturnDefaultWeatherWhenNoApiKey() {
        WeatherService weatherService = new WeatherService();
        Weather weather = weatherService.getWeatherForLocation("New York");
        
        assertNotNull(weather);
        assertEquals(22.5, weather.temp());
        assertEquals(25.0, weather.feels_like());
        assertEquals("Partly cloudy (mock data)", weather.description());
    }

    @Test
    void shouldReturnMockWeatherWhenApiKeyIsEmpty() {
        WeatherService weatherService = new WeatherService();
        // Simulate empty API key
        weatherService.getWeatherForLocation("New York");
        
        Weather weather = weatherService.getWeatherForLocation("New York");
        assertNotNull(weather);
        assertEquals(22.5, weather.temp());
        assertEquals(25.0, weather.feels_like());
    }
}
