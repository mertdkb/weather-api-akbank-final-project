package com.dikbiyik.weatherapi.appuser.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dikbiyik.weatherapi.appuser.AppUser;
import com.dikbiyik.weatherapi.appuser.repository.AppUserRepository;
import com.dikbiyik.weatherapi.exception.NotFoundException;
import com.dikbiyik.weatherapi.kafka.dto.KafkaLogMessageDto;
import com.dikbiyik.weatherapi.kafka.service.KafkaService;
import com.dikbiyik.weatherapi.openweathermap.WeatherDataResponse;
import com.dikbiyik.weatherapi.openweathermap.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository userRepository;

    private final WeatherService weatherService;

    private final KafkaService kafka;

    private final ObjectMapper objectMapper;

    public AppUser createUser(AppUser user) throws JsonProcessingException {
        KafkaLogMessageDto kafkaMessage = new KafkaLogMessageDto(user.getId(),
                " New user created with this username : " + user.getLogin());
        String message = objectMapper.writeValueAsString(kafkaMessage);
        kafka.sendMessage(message, "logs");

        return this.userRepository.save(user);
    }

    //returns user by jwt
    public AppUser getUser() throws JsonProcessingException {
        AppUser user = extractUser();

        KafkaLogMessageDto kafkaMessage = new KafkaLogMessageDto(user.getId(), " user with that id is returned");
        String message = objectMapper.writeValueAsString(kafkaMessage);
        kafka.sendMessage(message, "logs");
        
        return user;
    }

    public List<AppUser> getAllUsers() throws JsonProcessingException {
        // extract user details from jwt this is done here for just checking who made this request
        AppUser user = extractUser();

        //sends message to kafka
        KafkaLogMessageDto kafkaMessage = new KafkaLogMessageDto(user.getId(), " all users are returned");
        String message = objectMapper.writeValueAsString(kafkaMessage);
        kafka.sendMessage(message, "logs");

        return this.userRepository.findAll();
    }

    public void deleteUser() throws JsonProcessingException {
        // extracts user details from jwt
        AppUser user = extractUser();

        //deletes user from db
        this.userRepository.delete(user);

        //send message to kafka
        KafkaLogMessageDto kafkaMessage = new KafkaLogMessageDto(user.getId(), " this user is deleted : {}" + user);
        String message = objectMapper.writeValueAsString(kafkaMessage);
        kafka.sendMessage(message, "logs");
    }

    public Map<String, WeatherDataResponse> getUsersSavedCitiesWeatherData() throws JsonProcessingException {
        // extracts user details from jwt
        AppUser user = extractUser();

        //get saved cities of user and maps with cities weather data
        List<String> cities = user.getSavedCities();

        //key -> cityName, value -> weather data of this city
        Map<String, WeatherDataResponse> citiesData = new HashMap<>();

        //fills map with weather data
        for (String city : cities) {
            citiesData.put(city, weatherService.getWeather(city));
        }

        //sends message to kafka
        KafkaLogMessageDto kafkaMessage = new KafkaLogMessageDto(user.getId(), " saved cities of user are returned");
        String message = objectMapper.writeValueAsString(kafkaMessage);
        kafka.sendMessage(message, "logs");

        return citiesData;
    }

    public AppUser addSavedCityToAppUser(String city) throws JsonProcessingException {
        // extracts user details from jwt
        AppUser user = extractUser();

        // checks if user has any city or that list is null and if its null creates an
        // array list and adds city to user
        List<String> cities = user.getSavedCities();
        if (cities == null) {
            cities = new ArrayList<String>();
        }
        cities.add(city);
        user.setSavedCities(cities);
        userRepository.save(user);

        // sends message to kafka
        KafkaLogMessageDto kafkaMessage = new KafkaLogMessageDto(user.getId(),
                " new city saved to user with that id. Saved city : " + city);
        String message = objectMapper.writeValueAsString(kafkaMessage);
        kafka.sendMessage(message, "logs");

        return user;
    }

    public AppUser deleteSavedCityFromAppUser(String city) throws JsonProcessingException {
        // extracts user details from jwt
        AppUser user = extractUser();

        // gets saved cities of user and removes selected city
        List<String> cities = user.getSavedCities();
        cities.remove(city);
        user.setSavedCities(cities);
        userRepository.save(user);

        // sends message to kafka
        KafkaLogMessageDto kafkaMessage = new KafkaLogMessageDto(user.getId(),
                "saved city deleted from user. Deleted city : " + city);
        String message = objectMapper.writeValueAsString(kafkaMessage);
        kafka.sendMessage(message, "logs");

        return user;
    }

    private AppUser extractUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            AppUser user = userRepository.findByLogin(userDetails.getUsername()).orElseThrow();
            return user;
        } else {
            throw new NotFoundException("User cannot be found");
        }
    }

}
