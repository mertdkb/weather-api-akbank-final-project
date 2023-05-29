package com.dikbiyik.weatherapi.appuser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikbiyik.weatherapi.appuser.service.AppUserService;
import com.dikbiyik.weatherapi.generic.rest.GenericApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appuser")
public class AppUserController {
    
    private final AppUserService userService;

    @GetMapping("/{login}/savedcities")
    public GenericApiResponse getSavedCitiesOfUser(@PathVariable String login){
        var response = userService.getUsersSavedCitiesWeatherData(login);
        
        return new GenericApiResponse(200, "Success", "6549846654", response);
    }
}
