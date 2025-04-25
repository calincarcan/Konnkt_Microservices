package com.konnkt.database.controller;

import com.konnkt.database.entity.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.konnkt.database.dto.*;
import com.konnkt.database.service.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vote")
public class VoteController {
    private final VoteService voteService;

    @GetMapping("/getVotes")
    public ResponseEntity<List<VoteDto>> getVotes() {
        List<VoteDto> votes = voteService.findAll();
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/getVote/{id}")
    public ResponseEntity<VoteDto> getVote(@PathVariable Long id) {
        Optional<VoteDto> vote = voteService.findById(id);
        if (vote.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vote.get());
    }

    @PostMapping("/createVote")
    public ResponseEntity<VoteDto> createVote(@RequestBody InputVoteDto inputVoteDto) {
        Optional<VoteDto> vote = voteService.createVote(inputVoteDto);
        if (vote.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(vote.get());
    }

    @PutMapping("/changeVote/{id}")
    public ResponseEntity<VoteDto> changeVote(@PathVariable Long id) {
        Optional<VoteDto> vote = voteService.changeVote(id);
        if (vote.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vote.get());
    }

    @DeleteMapping("/deleteVote/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable Long id) {
        Boolean deleted = voteService.deleteVote(id);
        if (!deleted)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }
}
