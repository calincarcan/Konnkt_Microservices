package com.konnkt.database.controller;

import com.konnkt.database.dto.*;
import com.konnkt.database.service.PostService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/getPosts")
    public ResponseEntity<List<PostDto>> getPosts() {
        List<PostDto> posts = postService.findAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/getPost/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        Optional<PostDto> post = postService.findById(id);
        if (post.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(post.get());
    }

    @PostMapping("/createPost")
    public ResponseEntity<PostDto> createPost(@RequestBody InputPostDto inputPostDto) {
        Optional<PostDto> post = postService.createPost(inputPostDto);
        if (post.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(post.get());
    }

    @PutMapping("/updatePost/title/{id}")
    public ResponseEntity<PostDto> updatePostTitle(@PathVariable Long id, @RequestBody String title) {
        Optional<PostDto> post = postService.updatePostTitle(id, title);
        if (post.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(post.get());
    }

    @PutMapping("/updatePost/content/{id}")
    public ResponseEntity<PostDto> updatePostContent(@PathVariable Long id, @RequestBody String content) {
        Optional<PostDto> post = postService.updatePostContent(id, content);
        if (post.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(post.get());
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Boolean deleted = postService.deletePost(id);
        if (!deleted)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }
}
