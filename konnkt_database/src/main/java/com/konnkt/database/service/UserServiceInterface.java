package com.konnkt.database.service;

import com.konnkt.database.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    List<UserDto> findAll();
    Optional<UserDto> findById(Long id);
    Optional<UserDto> findByUsername(String username);
    Optional<UserDto> findByEmail(String email);
}