package com.dikbiyik.weatherapi.appuser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikbiyik.weatherapi.appuser.dto.DeleteSavedCityDto;
import com.dikbiyik.weatherapi.appuser.dto.PostSavedCityDto;
import com.dikbiyik.weatherapi.appuser.mapper.UserMapper;
import com.dikbiyik.weatherapi.appuser.service.AppUserService;
import com.dikbiyik.weatherapi.generic.rest.GenericApiResponse;

@RestController
@RequestMapping("/api/v1/appuser")
public class AppUserController {
    
    @Autowired
    private AppUserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public GenericApiResponse getUser(){
        var response = userMapper.userToDto(userService.getUserById());
        return new GenericApiResponse(200, "Success", "68486946646", response);
    }

    @DeleteMapping
    public GenericApiResponse deleteUser(){
        userService.deleteUser();
        return new GenericApiResponse(200, "Success", "879874");
    }

    @GetMapping("/weatherdata/savedcities")
    public GenericApiResponse getSavedCitiesWeatherDataOfUser(){
        var response = userService.getUsersSavedCitiesWeatherData();
        return new GenericApiResponse(200, "Success", "81941816", response);
    }

    @PostMapping("/new/savedcities")
    public GenericApiResponse addSavedCityToUser(@RequestBody PostSavedCityDto requestDto){
        var response = userService.addSavedCityToAppUser(requestDto.getCity());
        return new GenericApiResponse(200, "Success", "6549846654", response);
    }

    @DeleteMapping("/savedcities")
    public GenericApiResponse deleteSavedCityFromUser(@RequestBody DeleteSavedCityDto requestDto){
        var response = userService.deleteSavedCityFromAppUser(requestDto.getCity());
        return new GenericApiResponse(200, "Success", "564681351", response);
    }
}
