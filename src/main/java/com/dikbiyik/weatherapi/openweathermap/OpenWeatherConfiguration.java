package com.dikbiyik.weatherapi.openweathermap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "openweathermap")
public class OpenWeatherConfiguration {
    private String apiKey;
}
