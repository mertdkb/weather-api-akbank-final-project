package com.dikbiyik.weatherapi.appuser.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dikbiyik.weatherapi.appuser.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String>{
    
    
    @Query("select appuser from AppUser appuser where appuser.login LIKE CONCAT('%' , :query , '%')")
    Page<AppUser> findAllByQuery(String query, Pageable pageable);

    Optional<AppUser> findByLogin(String login);
}
