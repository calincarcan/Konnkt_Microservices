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
@RequestMapping("/api/vote")
public class VoteController {
    
    private final String dbUrl = "http://database-service:8093/api/vote";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/getVotes")
    public ResponseEntity<List<VoteDto>> getVotes() {
        String url = dbUrl + "/getVotes";
        ResponseEntity<List<VoteDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<VoteDto>>() {});
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/getVote/{id}")
    public ResponseEntity<VoteDto> getVote(@PathVariable Long id) {
        String url = dbUrl + "/getVote/" + id;
        ResponseEntity<VoteDto> response = restTemplate.getForEntity(url, VoteDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PostMapping("/createVote")
    public ResponseEntity<VoteDto> createVote(@RequestBody InputVoteDto inputVoteDto) {
        String url = dbUrl + "/createVote";
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        VoteDtoDB voteDtoDB = new VoteDtoDB(
                username,
                inputVoteDto.parentType(),
                inputVoteDto.parentId(),
                inputVoteDto.voteType()
        );
        HttpEntity<VoteDtoDB> requestEntity = new HttpEntity<>(voteDtoDB);
        ResponseEntity<VoteDto> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, VoteDto.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @PutMapping("/changeVote/{id}")
    public ResponseEntity<VoteDto> updateVote(@PathVariable Long id) {
        String url = dbUrl + "/changeVote/" + id;
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        ResponseEntity<VoteDto> response = restTemplate.exchange(
                url, HttpMethod.PUT, requestEntity, VoteDto.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @DeleteMapping("/deleteVote/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable Long id) {
        String url = dbUrl + "/deleteVote/" + id;
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.exchange(
                url, HttpMethod.DELETE, requestEntity, Void.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
