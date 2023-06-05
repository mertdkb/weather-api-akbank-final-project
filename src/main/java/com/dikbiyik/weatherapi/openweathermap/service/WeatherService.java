package com.dikbiyik.weatherapi.openweathermap.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dikbiyik.weatherapi.openweathermap.OpenWeatherConfiguration;
import com.dikbiyik.weatherapi.openweathermap.WeatherDataResponse;
import com.dikbiyik.weatherapi.openweathermap.client.WeatherFeignClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

    private OpenWeatherConfiguration configuration;

    private final WeatherFeignClient feignClient;

    public WeatherService(WeatherFeignClient feignClient, OpenWeatherConfiguration configuration) {
        this.feignClient = feignClient;
        this.configuration = configuration;
    }

    public WeatherDataResponse getWeather(String q) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            WeatherDataResponse responseData = objectMapper.readValue(feignClient.weatherData(q, configuration.getApiKey()),WeatherDataResponse.class);

            return responseData;
        } catch (
                JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}