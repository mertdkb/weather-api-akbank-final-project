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
import com.dikbiyik.weatherapi.kafka.service.KafkaService;
import com.dikbiyik.weatherapi.openweathermap.WeatherDataResponse;
import com.dikbiyik.weatherapi.openweathermap.service.WeatherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository userRepository;

    private final WeatherService weatherService;

    private final KafkaService kafka;

    public AppUser createUser(AppUser user) {
        kafka.sendMessage("user created", "logs");
        return this.userRepository.save(user);
    }

    public AppUser getUserById() {
        AppUser user = extractUser();
        return user;
    }

    public AppUser getUserByLogin(String login) {
        AppUser user = extractUser();
        return user;
    }

    public List<AppUser> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void deleteUser() {
        AppUser user = extractUser();
        this.userRepository.delete(user);
    }

    public Map<String, WeatherDataResponse> getUsersSavedCitiesWeatherData() {
        AppUser user = extractUser();
        List<String> cities = user.getSavedCities();
        Map<String, WeatherDataResponse> citiesData = new HashMap<>();

        for (String city : cities) {
            citiesData.put(city, weatherService.getWeather(city));
        }
        return citiesData;
    }

    public AppUser addSavedCityToAppUser(String city) {
        AppUser user = extractUser();
        List<String> cities = user.getSavedCities();
        if (cities == null) {
            cities = new ArrayList<String>();
        }
        cities.add(city);
        user.setSavedCities(cities);
        userRepository.save(user);
        return user;
    }

    public AppUser deleteSavedCityFromAppUser(String city) {
        AppUser user = extractUser();
        List<String> cities = user.getSavedCities();
        cities.remove(city);
        user.setSavedCities(cities);
        userRepository.save(user);
        return user;
    }

    private AppUser extractUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof UserDetails){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            AppUser user = userRepository.findByLogin(userDetails.getUsername()).orElseThrow();
            return user;
        }
        else{
            throw new NotFoundException("User cannot be found");
        }
    }

}
