package com.dikbiyik.weatherapi.appuser.mapper;

import org.mapstruct.Mapper;

import com.dikbiyik.weatherapi.appuser.AppUser;
import com.dikbiyik.weatherapi.appuser.dto.GetAppUserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    GetAppUserDto userToDto(AppUser appUser);
}
