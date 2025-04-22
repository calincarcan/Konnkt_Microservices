package com.konnkt.backend.controller;

import com.konnkt.backend.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.*;
import org.springframework.web.bind.annotation.PathVariable;
import com.konnkt.backend.dto.UserDto;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getUsers") 
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUser/username/{username}")
    public ResponseEntity<Optional<UserDto>> getUserByUsername(@PathVariable String username) {
        Optional<UserDto> user = userService.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getUser/email/{email}")
    public ResponseEntity<Optional<UserDto>> getUserByEmail(String email) {
        Optional<UserDto> user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getUser/id/{id}")
    public ResponseEntity<Optional<UserDto>> getUserById(Long id) {
        Optional<UserDto> user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}