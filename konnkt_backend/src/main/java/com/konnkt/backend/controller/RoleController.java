package com.konnkt.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.konnkt.backend.dto.RoleDto;
import org.springframework.core.ParameterizedTypeReference;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    
    final String dbUrl = "http://database-service:8093/api/role";
    final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/getRoles")
    public ResponseEntity<List<RoleDto>> getRoles() {
        String url = dbUrl + "/getRoles";
        ResponseEntity<List<RoleDto>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<RoleDto>>() {});
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/getRole/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        String url = dbUrl + "/getRole/" + id;
        ResponseEntity<RoleDto> response = restTemplate.getForEntity(url, RoleDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
