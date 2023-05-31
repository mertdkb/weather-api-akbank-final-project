package com.dikbiyik.weatherapi.appuser.dto;

import java.util.List;

import com.dikbiyik.weatherapi.appuser.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAppUserDto {
    private String login;

    private List<String> savedCities;

    private Role role;
}
