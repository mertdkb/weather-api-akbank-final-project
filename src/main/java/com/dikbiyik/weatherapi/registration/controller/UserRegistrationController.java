package com.dikbiyik.weatherapi.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikbiyik.weatherapi.appuser.AppUser;
import com.dikbiyik.weatherapi.appuser.dto.UserRegistrationDto;
import com.dikbiyik.weatherapi.appuser.mapper.UserMapper;
import com.dikbiyik.weatherapi.appuser.repository.AppUserRepository;
import com.dikbiyik.weatherapi.appuser.service.AppUserRegistrationService;
import com.dikbiyik.weatherapi.configuration.JwtService;
import com.dikbiyik.weatherapi.generic.rest.GenericApiResponse;
import com.dikbiyik.weatherapi.registration.dto.AuthenticationRequestDto;


@RestController
@RequestMapping("/api/v1/auth/user-registrations")
public class UserRegistrationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private AppUserRegistrationService userRegistrationService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/app-user")
    public GenericApiResponse createAppUser(@RequestBody AuthenticationRequestDto authRequestDto) {
        AppUser user = userRegistrationService.registerAppUser(authRequestDto);
        var response = userMapper.userToDto(user);
        return new GenericApiResponse(200, "Success", "54987463", response);
    }

    @PostMapping("/authenticate")
    public GenericApiResponse authenticate(@RequestBody AuthenticationRequestDto request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        var user = userRepository.findByLogin(request.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new GenericApiResponse(200, "Success", "51687431", jwtToken);
    }
}
