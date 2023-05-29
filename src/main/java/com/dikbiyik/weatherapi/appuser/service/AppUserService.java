package com.dikbiyik.weatherapi.appuser.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dikbiyik.weatherapi.appuser.AppUser;
import com.dikbiyik.weatherapi.appuser.repository.AppUserRepository;
import com.dikbiyik.weatherapi.openweathermap.WeatherDataResponse;
import com.dikbiyik.weatherapi.openweathermap.service.WeatherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository userRepository;

    private final WeatherService weatherService;

    public AppUser createUser(AppUser user) {
        return this.userRepository.save(user);
    }

    public AppUser getUserById(String id){
        return this.userRepository.findById(id).orElseThrow();
    }

    public AppUser getUserByLogin(String login){
        return this.userRepository.findByLogin(login).orElseThrow();
    }

    public List<AppUser> getAllUsers(){
        return this.userRepository.findAll();
    }

    public void deleteUser(AppUser user){
        this.userRepository.delete(user);
    }

    public void deleteUserById(String id){
        this.userRepository.deleteById(id);
    }

    public void deleteUserByLogin(String login){
        AppUser user = getUserByLogin(login);
        deleteUser(user);
    }
    
    public Map<String, WeatherDataResponse> getUsersSavedCitiesWeatherData(String login){
        AppUser user = getUserByLogin(login);
        List<String> cities = user.getSavedCities();
        Map<String, WeatherDataResponse> citiesData = new HashMap<>();

        for (String city : cities) {
            citiesData.put(city, weatherService.getWeather(city));
        }
        return citiesData;
        
    }
    
}
