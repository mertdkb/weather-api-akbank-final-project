package com.dikbiyik.weatherapi.openweathermap.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name ="openweathermap", url= "api.openweathermap.org/data/2.5/forecast")
public interface WeatherFeignClient {

    @GetMapping
    String weatherData(@RequestParam String q, @RequestParam String appid);

}