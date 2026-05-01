package com.codewiz.eventbooking.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.codewiz.eventbooking.dto.Weather;

@Service
public class WeatherService {

    private final RestClient restClient;
    private final Map<String, Weather> weatherCache = new ConcurrentHashMap<>();

    @Value("${weather.api.key:}")
    private String apiKey;

    @Value("${weather.api.base-url:https://api.openweathermap.org}")
    private String apiBaseUrl = "https://api.openweathermap.org";

    public WeatherService() {
        this.restClient = RestClient.create();
    }

    public Weather getWeatherForLocation(String location) {
        if (weatherCache.containsKey(location)) {
            return weatherCache.get(location);
        }
        String geoUrl = apiBaseUrl + "/geo/1.0/direct?q={location}&limit=1&appid={apiKey}";
        record GeoData(double lat, double lon) {}
        List<GeoData> geoDataList = restClient.get()
                .uri(geoUrl, location, apiKey)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        GeoData geoData = geoDataList.get(0);
        Double lat = geoData.lat;
        Double lon = geoData.lon;

        String weatherUrl = apiBaseUrl + "/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}";
        record MainData(Double temp, Double feels_like, Double temp_min, Double temp_max, Integer humidity) {}
        record WeatherInfo(String description) {}
        record WeatherResponse(MainData main, List<WeatherInfo> weather) {}
        
        WeatherResponse weatherResponse = restClient.get()
                .uri(weatherUrl, lat, lon, apiKey)
                .retrieve()
                .body(WeatherResponse.class);
        
        MainData main = weatherResponse.main();
        WeatherInfo weatherInfo = weatherResponse.weather().get(0);
        
        Weather weatherData = new Weather(
            main.temp(), 
            main.feels_like(), 
            main.temp_min(), 
            main.temp_max(), 
            main.humidity(), 
            weatherInfo.description()
        );
        weatherCache.put(location, weatherData);
        return weatherData;

    }

}
