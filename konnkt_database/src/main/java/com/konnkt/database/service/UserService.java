package com.konnkt.database.service;

import com.konnkt.database.dto.UserDto;
import com.konnkt.database.entity.User;
import com.konnkt.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;

    private UserDto EntityToDto(User user) {
        return new UserDto(user.getUserId(), user.getUsername(), user.getEmail());
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
            .map(this::EntityToDto)
            .toList();
    }

    public Optional<UserDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
            .map(this::EntityToDto);
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
            .map(this::EntityToDto);
    }

    public Optional<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(this::EntityToDto);
    }
}