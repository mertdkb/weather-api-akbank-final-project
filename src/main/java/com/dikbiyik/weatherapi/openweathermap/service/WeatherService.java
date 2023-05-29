package com.dikbiyik.weatherapi.openweathermap.service;

import org.springframework.stereotype.Service;

import com.dikbiyik.weatherapi.openweathermap.WeatherData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {


    private final String apiKey = "a748d7cb922d8fe594bb609d1ce8a144";
    private final WeatherFeignClient feignClient;

    public WeatherService(WeatherFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    public WeatherData getWeather(String q) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            WeatherData responseData = objectMapper.readValue(feignClient.weatherData(q, apiKey),WeatherData.class);

            return responseData;
        } catch (
                JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}