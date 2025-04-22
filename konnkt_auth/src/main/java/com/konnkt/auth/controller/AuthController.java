package com.konnkt.auth.controller;

import com.konnkt.auth.service.AuthService;
import com.konnkt.auth.dto.LoginDto;
import com.konnkt.auth.dto.RegisterDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        logger.debug("Request to register user {}", registerDto.getEmail());
        authService.register(registerDto);
        logger.debug("Successfully registered user {}", registerDto.getEmail());
        return new ResponseEntity<>("User registered", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        logger.debug("Request to login for user {}", loginDto.getLoginInfo());
        String token = authService.login(loginDto);
        logger.debug("Successfully logged in user {}", loginDto.getLoginInfo());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @SecurityRequirement(name="bearerAuth")
    @GetMapping("/getLogged")
    public ResponseEntity<?> getUserAuthorities() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("username", username);
        userDetails.put("authorities", auth);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
}
