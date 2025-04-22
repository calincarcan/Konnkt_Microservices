package com.konnkt.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final String dbUrl = "http://database-service:8093/api/user";
    private final RestTemplate restTemplate = new RestTemplate();
    
    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers() {
        String url = dbUrl + "/getUsers";
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, null, Object.class);
        return response;
    }

    @GetMapping("/getUser/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        String url = dbUrl + "/getUser/id/" + id;
        ResponseEntity<?> response = restTemplate.getForEntity(url, Object.class);
        return response;
    }

    @GetMapping("/getUser/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        String url = dbUrl + "/getUser/username/" + username;
        ResponseEntity<?> response = restTemplate.getForEntity(url, Object.class);
        return response;
    }

    @GetMapping("/getUser/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        String url = dbUrl + "/getUser/email/" + email;
        ResponseEntity<?> response = restTemplate.getForEntity(url, Object.class);
        return response;
    }
    
    
}
