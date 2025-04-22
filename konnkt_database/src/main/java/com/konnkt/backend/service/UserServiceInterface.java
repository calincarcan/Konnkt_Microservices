package com.konnkt.backend.service;

import com.konnkt.backend.dto.UserDto;
import com.konnkt.backend.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    List<UserDto> findAll();
    Optional<UserDto> findById(Long id);
    Optional<UserDto> findByUsername(String username);
    Optional<UserDto> findByEmail(String email);
}