package com.konnkt.backend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello simple!";
    }

    @GetMapping("/test")
    @SecurityRequirement(name="bearerAuth")
    public String hello() {
        return "Hello JWT!";
    }

    @GetMapping("/securedtest")
    @PreAuthorize("hasAuthority('admin')")
    @SecurityRequirement(name="bearerAuth")
    public String test() {
        return "Hello JWT ADMIN!";
    }

    @GetMapping("/DEBUG")
    @SecurityRequirement(name="bearerAuth")
    public ResponseEntity<Map<String, Object>> getUserDetails() {
        Map<String, Object> user = new HashMap<>();
        user.put("username", SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        user.put("authorities", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        
        return ResponseEntity.ok(user);
    }
}
