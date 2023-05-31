package com.dikbiyik.weatherapi.appuser.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dikbiyik.weatherapi.appuser.AppUser;
import com.dikbiyik.weatherapi.appuser.Role;
import com.dikbiyik.weatherapi.registration.dto.AuthenticationRequestDto;

@Service
public class AppUserRegistrationService {
    
    private AppUserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUserRegistrationService(AppUserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public AppUser registerAppUser(AuthenticationRequestDto authRequestDto){

        AppUser user = new AppUser();
        user.setLogin(authRequestDto.getLogin());
        user.setPassword(bCryptPasswordEncoder.encode(authRequestDto.getPassword()));
        user.setRole(Role.APPUSER);

        return this.userService.createUser(user);
    }   
}
