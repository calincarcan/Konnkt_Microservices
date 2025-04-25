package com.konnkt.database.controller;

import com.konnkt.database.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.konnkt.database.dto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/getComments")
    public ResponseEntity<List<CommentDto>> getComments() {
        List<CommentDto> comments = commentService.findAll();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/getComment/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long id) {
        Optional<CommentDto> comment = commentService.findById(id);
        if (comment.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(comment.get());
    }

    @PostMapping("/createComment")
    public ResponseEntity<CommentDto> createComment(@RequestBody InputCommentDto inputCommentDto) {
        Optional<CommentDto> comment = commentService.createComment(inputCommentDto);
        if (comment.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(comment.get());
    }

    @PutMapping("/updateComment/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody String content) {
        Optional<CommentDto> comment = commentService.updateComment(id, content);
        if (comment.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(comment.get());
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        Boolean deleted = commentService.deleteComment(id);
        if (!deleted)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }
}
