package com.konnkt.backend.controller;

import com.konnkt.backend.dto.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final String dbUrl = "http://database-service:8093/api/comment";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/getComments")
    public ResponseEntity<List<CommentDto>> getComments() {
        String url = dbUrl + "/getComments";
        ResponseEntity<List<CommentDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CommentDto>>() {});
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/getComment/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long id) {
        String url = dbUrl + "/getComment/" + id;
        ResponseEntity<CommentDto> response = restTemplate.getForEntity(url, CommentDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PostMapping("/createComment")
    public ResponseEntity<CommentDto> createComment(@RequestBody InputCommentDto inputCommentDto) {
        String url = dbUrl + "/createComment";
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        CommentDtoDB commentDtoDB = new CommentDtoDB(
                inputCommentDto.content(),
                username,
                inputCommentDto.postId()
        );
        HttpEntity<CommentDtoDB> requestEntity = new HttpEntity<>(commentDtoDB);
        ResponseEntity<CommentDto> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, CommentDto.class);
        
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PutMapping("/updateComment/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody String content) {
        String url = dbUrl + "/updateComment/" + id;
        HttpEntity<String> requestEntity = new HttpEntity<>(content);
        ResponseEntity<CommentDto> response = restTemplate.exchange(
                url, HttpMethod.PUT, requestEntity, CommentDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        String url = dbUrl + "/deleteComment/" + id;
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.exchange(
                url, HttpMethod.DELETE, requestEntity, Void.class);
        return ResponseEntity.status(response.getStatusCode()).build();
    }
}
