package com.dikbiyik.weatherapi.appuser.service;

import org.springframework.stereotype.Service;

import com.dikbiyik.weatherapi.appuser.AppUser;
import com.dikbiyik.weatherapi.appuser.repository.AppUserRepository;

@Service
public class AppUserService {

    private AppUserRepository userRepository;

    public AppUserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser createUser(AppUser user) {
        return this.userRepository.save(user);
    }
    
}
