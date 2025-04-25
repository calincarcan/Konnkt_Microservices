package com.konnkt.database.controller;

import com.konnkt.database.dto.ForumDto;
import com.konnkt.database.dto.InputForumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.konnkt.database.service.ForumService;

import java.util.*;

@RestController
@RequestMapping("/api/forum")
@RequiredArgsConstructor
public class ForumController {
    private final ForumService forumService;

    @GetMapping("/getForums")
    public ResponseEntity<List<ForumDto>> getForums() {
        List<ForumDto> forums = forumService.findAll();
        return ResponseEntity.ok(forums);
    }

    @GetMapping("/getForum/id/{id}")
    public ResponseEntity<ForumDto> getForumById(@PathVariable Long id) {
        Optional<ForumDto> forum  = forumService.findById(id);
        if (forum.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(forum.get());
    }

    @GetMapping("/getForum/name/{name}")
    public ResponseEntity<ForumDto> getForumByName(@PathVariable String name) {
        Optional<ForumDto> forum = forumService.findByName(name);
        if (forum.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(forum.get());
    }

    @PostMapping("/createForum")
    public ResponseEntity<ForumDto> createForum(@RequestBody InputForumDto inputForumDto) {
        Optional<ForumDto> forum = forumService.createForum(inputForumDto);
        if (forum.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(forum.get());

    }

    @PutMapping("/updateForum/name/{id}")
    public ResponseEntity<ForumDto> updateForumName(@PathVariable Long id, @RequestBody String name) {
        Optional<ForumDto> forum = forumService.updateForumName(id, name);
        if (forum.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(forum.get());
    }

    @PutMapping("/updateForum/desc/{id}")
    public ResponseEntity<ForumDto> updateForumDescription(@RequestBody Long id, @RequestBody String desc) {
        Optional<ForumDto> forum = forumService.updateForumDescription(id, desc);
        if (forum.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(forum.get());
    }

    @DeleteMapping("/deleteForum/id/{id}")
    public ResponseEntity<Void> deleteForumById(@PathVariable Long id) {
        Boolean deleted = forumService.deleteForumById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteForum/name/{name}")
    public ResponseEntity<Void> deleteForumByName(@PathVariable String name) {
        Boolean deleted = forumService.deleteForumByName(name);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
