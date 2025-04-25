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
import org.springframework.security.core.context.SecurityContextHolder;
import com.konnkt.backend.dto.UserInputPostDto;
import com.konnkt.backend.dto.InputPostDto;
import com.konnkt.backend.dto.PostDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.core.ParameterizedTypeReference;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/post")
public class PostController {
    final String dbUrl = "http://database-service:8093/api/post";
    final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/getPosts")
    public ResponseEntity<List<PostDto>> getPosts() {
        String url = dbUrl + "/getPosts";
        ResponseEntity<List<PostDto>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<PostDto>>() {});
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/getPost/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        String url = dbUrl + "/getPost/" + id;
        ResponseEntity<PostDto> response = restTemplate.getForEntity(url, PostDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestBody UserInputPostDto post) {
        String url = dbUrl + "/createPost";
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        InputPostDto postDB = new InputPostDto(post.title(), post.content(), username, post.forumId());
        ResponseEntity<?> response = restTemplate.postForEntity(url, postDB, Object.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
    
    @PutMapping("/updatePost/title/{id}")
    public ResponseEntity<PostDto> updatePostTitle(@PathVariable Long id, @RequestBody String title) {
        String url = dbUrl + "/updatePost/title/" + id;
        HttpEntity<String> request = new HttpEntity<>(title);
        ResponseEntity<PostDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, PostDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PutMapping("/updatePost/content/{id}")
    public ResponseEntity<PostDto> updatePostContent(@PathVariable Long id, @RequestBody String content) {
        String url = dbUrl + "/updatePost/content/" + id;
        HttpEntity<String> request = new HttpEntity<>(content);
        ResponseEntity<PostDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, PostDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        String url = dbUrl + "/deletePost/" + id;
        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
        return ResponseEntity.status(response.getStatusCode()).build();
    }
}
