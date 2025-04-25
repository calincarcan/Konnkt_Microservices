package com.konnkt.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.konnkt.backend.dto.ForumDto;
import com.konnkt.backend.dto.InputForumDto;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.core.ParameterizedTypeReference;
import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    final String dbUrl = "http://database-service:8093/api/forum";
    final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/getForums")
    public ResponseEntity<List<ForumDto>> getForums() {
        String url = dbUrl + "/getForums";
        ResponseEntity<List<ForumDto>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ForumDto>>() {});
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/getForums/id/{id}")
    public ResponseEntity<ForumDto> getForumById(@PathVariable Long id) {
        String url = dbUrl + "/getForum/" + id;
        ResponseEntity<ForumDto> response = restTemplate.getForEntity(url, ForumDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/getForums/name/{name}")
    public ResponseEntity<ForumDto> getForumByName(@PathVariable String name) {
        String url = dbUrl + "/getForum/name/" + name;
        ResponseEntity<ForumDto> response = restTemplate.getForEntity(url, ForumDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
    
    // Create a new forum
    @PostMapping("/createForum")
    public ResponseEntity<ForumDto> createForum(@RequestBody InputForumDto inputForumDto) {
    String url = dbUrl + "/createForum";
    HttpEntity<InputForumDto> requestEntity = new HttpEntity<>(inputForumDto);
    ResponseEntity<ForumDto> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ForumDto.class);
    return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    // Update an existing forum
    @PutMapping("/updateForum/name/{id}")
    public ResponseEntity<ForumDto> updateForum(@PathVariable Long id, @RequestBody String name) {
        String url = dbUrl + "/updateForum/name/" + id;
        HttpEntity<String> requestEntity = new HttpEntity<>(name);
        ResponseEntity<ForumDto> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ForumDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PutMapping("/updateForum/desc/{id}")
    public ResponseEntity<ForumDto> updateForumDesc(@PathVariable Long id, @RequestBody String description) {
        String url = dbUrl + "/updateForum/desc/" + id;
        HttpEntity<String> requestEntity = new HttpEntity<>(description);
        ResponseEntity<ForumDto> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, ForumDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @DeleteMapping("/deleteForum/id/{id}")
    public ResponseEntity<Void> deleteForum(@PathVariable Long id) {
        String url = dbUrl + "/deleteForum/" + id;
        restTemplate.delete(url);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteForum/name/{name}")
    public ResponseEntity<Void> deleteForumByName(@PathVariable String name) {
        String url = dbUrl + "/deleteForum/name/" + name;
        restTemplate.delete(url);
        return ResponseEntity.noContent().build();
    }
}
