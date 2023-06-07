package com.dikbiyik.weatherapi.appuser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/v1/appuser")
public class AppUserController {

    @Autowired
    private AppUserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public GenericApiResponse getUser() throws JsonProcessingException {
        var response = userMapper.userToDto(userService.getUser());
        return new GenericApiResponse(HttpStatus.OK.value(), "Success", "68486946646", response);
    }

    @DeleteMapping
    public GenericApiResponse deleteUser() throws JsonProcessingException {
        userService.deleteUser();
        return new GenericApiResponse(HttpStatus.OK.value(), "Success", "879874");
    }

    @GetMapping("/weatherdata/savedcities")
    public GenericApiResponse getSavedCitiesWeatherDataOfUser() throws JsonProcessingException {
        var response = userService.getUsersSavedCitiesWeatherData();
        return new GenericApiResponse(HttpStatus.OK.value(), "Success", "81941816", response);
    }

    @PostMapping("/new/savedcities")
    public GenericApiResponse addSavedCityToUser(@RequestBody PostSavedCityDto requestDto)
            throws JsonProcessingException {
        var response = userService.addSavedCityToAppUser(requestDto.getCity());
        return new GenericApiResponse(HttpStatus.OK.value(), "Success", "6549846654", response);
    }

    @DeleteMapping("/savedcities")
    public GenericApiResponse deleteSavedCityFromUser(@RequestBody DeleteSavedCityDto requestDto)
            throws JsonProcessingException {
        var response = userService.deleteSavedCityFromAppUser(requestDto.getCity());
        return new GenericApiResponse(HttpStatus.OK.value(), "Success", "564681351", response);
    }
}
