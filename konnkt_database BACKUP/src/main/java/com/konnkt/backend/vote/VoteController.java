package com.konnkt.backend.vote;

import com.konnkt.backend.post.PostController;

import com.konnkt.backend.post.dto.PublicPostDto;
import com.konnkt.backend.vote.dto.PublicVoteDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;
    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/getVotes")
    public ResponseEntity<?> getVotes() {
        List<PublicVoteDto> votes = voteService.getAllVotes();
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }

    @GetMapping("/getVote/{id}")
    public ResponseEntity<?> getVoteById(@PathVariable Long id) {
        PublicVoteDto vote = voteService.getVoteById(id);
        return new ResponseEntity<>(vote, HttpStatus.OK);
    }

    @SecurityRequirement(name="bearerAuth")
    @PostMapping("/createVote")
    public ResponseEntity<?> createVote(@RequestBody PublicVoteDto vote) {
        logger.info("Creating new vote");
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        voteService.createVote(loggedUsername, vote);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SecurityRequirement(name="bearerAuth")
    @PutMapping("/changeVote/{id}")
    public ResponseEntity<?> changeVote(@PathVariable Long id) {
        logger.info("Changing vote with id {}", id);
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        voteService.changeVote(id, loggedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SecurityRequirement(name="bearerAuth")
    @DeleteMapping("/deleteVote/{id}")
    public ResponseEntity<?> deleteVote(@PathVariable Long id) {
        logger.info("Deleting vote with id {}", id);
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        voteService.deleteVote(id, loggedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
