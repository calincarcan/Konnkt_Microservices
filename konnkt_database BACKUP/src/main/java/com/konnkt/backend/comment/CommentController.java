package com.konnkt.backend.comment;

import com.konnkt.backend.comment.dto.PublicCommentDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getComments")
    public ResponseEntity<?> getComments() {
        logger.info("Getting all comments");
        List<PublicCommentDto> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/getComment/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        logger.info("Getting comment with id {}", id);
        PublicCommentDto comment = commentService.getCommentById(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/getScore/{id}")
    public ResponseEntity<?> getScore(@PathVariable Long id) {
        logger.info("Getting score of comment with id {}", id);
        int score = commentService.getCommentScore(id);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }


    @SecurityRequirement(name="bearerAuth")
    @PostMapping("/addCommentToPost/{postId}")
    public ResponseEntity<?> createComment(@RequestBody String content, @PathVariable Long postId) {
        logger.info("Creating new comment");
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.createComment(loggedUser, postId, content);
        return new ResponseEntity<>(content, HttpStatus.CREATED);
    }


    @SecurityRequirement(name="bearerAuth")
    @PutMapping("/updateComment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody String content) {
        logger.info("Updating comment with id {}", id);
        commentService.changeContent(id, content);
        PublicCommentDto comment = commentService.getCommentById(id);
        return new ResponseEntity<>(comment, HttpStatus.ACCEPTED);
    }


    @SecurityRequirement(name="bearerAuth")
    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        logger.info("Deleting comment with id {}", id);
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.deleteComment(id, loggedUser);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
