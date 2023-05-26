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
import com.dikbiyik.weatherapi.appuser.repository.AppUserRepository;
import com.dikbiyik.weatherapi.appuser.service.AppUserRegistrationService;
import com.dikbiyik.weatherapi.auth.AuthenticationRequest;
import com.dikbiyik.weatherapi.configuration.JwtService;
import com.dikbiyik.weatherapi.generic.rest.GenericApiResponse;


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

    @PostMapping("/app-user")
    public GenericApiResponse createServiceProviderOrganizationAdminUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        AppUser user = userRegistrationService.registerAppUser(userRegistrationDto.getLogin(), userRegistrationDto.getPassword());

        return new GenericApiResponse(200, "Success", "54987463", user);
    }

    @PostMapping("/authenticate")
    public GenericApiResponse authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        var user = userRepository.findByLogin(request.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new GenericApiResponse(200, "Success", "51687431", jwtToken);
    }
}
