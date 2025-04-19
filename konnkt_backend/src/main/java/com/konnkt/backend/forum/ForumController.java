package com.konnkt.backend.forum;

import com.konnkt.backend.forum.dto.PublicForumDto;
import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/forums")
public class ForumController {

    private final ForumService forumService;
    private static final Logger logger = LoggerFactory.getLogger(ForumController.class);

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/getForums")
    public ResponseEntity<?> getAllForums() {
        logger.info("Getting all forums");
        List<PublicForumDto> forums = forumService.getAllForums();
        return new ResponseEntity<>(forums, HttpStatus.OK);
    }

    @GetMapping("/getForum/{id}")
    public ResponseEntity<?> getForumById(@PathVariable Long id) {
        logger.info("Getting forum with id {}", id);
        PublicForumDto forum = forumService.getForumById(id);
        return new ResponseEntity<>(forum, HttpStatus.OK);
    }


    @SecurityRequirement(name="bearerAuth")
    @PostMapping("/createForum")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> createForum(@RequestBody PublicForumDto forum) {
        logger.info("Creating new forum");
         forumService.createForum(forum);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SecurityRequirement(name="bearerAuth")
    @PutMapping("/updateForumName/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateForumName(@PathVariable Long id, @RequestBody String newName) {
        logger.info("Updating forum name for forum with id {}", id);
        forumService.updateForumName(id, newName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SecurityRequirement(name="bearerAuth")
    @PutMapping("/updateForumDescription/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateForumDescription(@PathVariable Long id, @RequestBody String newDescription) {
        logger.info("Updating forum description for forum with id {}", id);
        forumService.updateForumDescription(id, newDescription);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SecurityRequirement(name="bearerAuth")
    @DeleteMapping("/deleteForum/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteForum(@PathVariable Long id) {
        logger.info("Deleting forum with id {}", id);
        forumService.deleteForum(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
