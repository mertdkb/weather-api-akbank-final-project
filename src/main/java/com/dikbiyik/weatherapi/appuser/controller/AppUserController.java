package com.dikbiyik.weatherapi.appuser.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikbiyik.weatherapi.appuser.dto.DeleteSavedCityDto;
import com.dikbiyik.weatherapi.appuser.dto.PostSavedCityDto;
import com.dikbiyik.weatherapi.appuser.service.AppUserService;
import com.dikbiyik.weatherapi.generic.rest.GenericApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appuser")
public class AppUserController {
    
    private final AppUserService userService;

    @GetMapping("/{login}")
    public GenericApiResponse getUser(@PathVariable String login){
        var response = userService.getUserByLogin(login);

        return new GenericApiResponse(200, "Success", "68486946646", response);
    }

    @DeleteMapping("/{login}/del")
    public GenericApiResponse deleteUser(@PathVariable String login){
        userService.deleteUserByLogin(login);

        return new GenericApiResponse(200, "Success", "879874");
    }

    @GetMapping("/{login}/savedcities")
    public GenericApiResponse getSavedCitiesOfUser(@PathVariable String login){

        var response = userService.getUsersSavedCitiesWeatherData(login);
        
        return new GenericApiResponse(200, "Success", "81941816", response);
    }

    @PostMapping("/{login}/new/savedcities")
    public GenericApiResponse addSavedCityToUser(@PathVariable String login, @RequestBody PostSavedCityDto requestDto){
        var response = userService.addSavedCityToAppUser(login, requestDto.getCity());
        
        return new GenericApiResponse(200, "Success", "6549846654", response);
    }

    @DeleteMapping("/{login}/del/savedcities")
    public GenericApiResponse deleteSavedCityFromUser(@PathVariable String login, @RequestBody DeleteSavedCityDto requestDto){
        var response = userService.deleteSavedCityFromAppUser(login, requestDto.getCity());

        return new GenericApiResponse(200, "Success", "564681351", response);
    }
}
