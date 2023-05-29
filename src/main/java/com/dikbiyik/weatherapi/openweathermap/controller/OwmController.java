package com.dikbiyik.weatherapi.openweathermap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dikbiyik.weatherapi.generic.rest.GenericApiResponse;
import com.dikbiyik.weatherapi.openweathermap.WeatherData;
import com.dikbiyik.weatherapi.openweathermap.WeatherData.Weather;
import com.dikbiyik.weatherapi.openweathermap.service.WeatherFeignClient;
import com.dikbiyik.weatherapi.openweathermap.service.WeatherService;


@RestController
@RequestMapping("api/v1/weather")
public class OwmController {

    private WeatherService weatherService;

    public OwmController(WeatherService weatherService){
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/current")
    public GenericApiResponse getCurrentTempOfCity(@RequestParam String city) {

        WeatherData weatherData = weatherService.getWeather(city);

        return new GenericApiResponse(200, "Success", "44894654", weatherData);
    }

}
