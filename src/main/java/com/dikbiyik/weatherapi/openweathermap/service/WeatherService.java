package com.dikbiyik.weatherapi.openweathermap.service;

import org.springframework.stereotype.Service;

import com.dikbiyik.weatherapi.openweathermap.WeatherDataResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {


    private final String apiKey = "a748d7cb922d8fe594bb609d1ce8a144";
    private final WeatherFeignClient feignClient;

    public WeatherService(WeatherFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    public WeatherDataResponse getWeather(String q) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            WeatherDataResponse responseData = objectMapper.readValue(feignClient.weatherData(q, apiKey),WeatherDataResponse.class);

            return responseData;
        } catch (
                JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}