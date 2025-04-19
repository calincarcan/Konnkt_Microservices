package com.konnkt.backend.admin;

import com.konnkt.backend.admin.dto.LoginDto;
import com.konnkt.backend.admin.dto.LoginResponseDto;
import com.konnkt.backend.admin.dto.RegisterDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final LoginResponseDto loginResponseDto;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService, LoginResponseDto loginResponseDto) {
        this.authService = authService;
        this.loginResponseDto = loginResponseDto;
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
        return new ResponseEntity<>(loginResponseDto.setToken(token), HttpStatus.OK);
    }

    @SecurityRequirement(name="bearerAuth")
    @GetMapping("/token")
    public ResponseEntity<?> validateToken() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug("Successfully validated token for user {}", user.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SecurityRequirement(name="bearerAuth")
    @GetMapping("/user-authorities")
    public ResponseEntity<?> getUserAuthorities() {
        logger.debug("Request to get user authorities");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug("Successfully retrieved user authorities");
        return new ResponseEntity<>("User Authorities: " + userDetails.getAuthorities(), HttpStatus.OK);
    }

//    @GetMapping("/hello")
//    public ResponseEntity<?> hello() {
//        return new ResponseEntity<>("Hello, world!", HttpStatus.OK);
//    }
//
//    @GetMapping("/test")
//    public ResponseEntity<?> test() {
//        RestTemplate restTemplate = new RestTemplate();
//        String r = restTemplate.getForObject("http://localhost:8090/api/auth/hello", String.class);
//        return new ResponseEntity<>(r, HttpStatus.OK);
//    }
}
