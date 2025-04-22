package com.konnkt.backend.post;

import com.konnkt.backend.post.dto.NewPostDto;
import com.konnkt.backend.post.dto.PublicPostDto;
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
@RequestMapping("/api/posts")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @SecurityRequirement(name="bearerAuth")
    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestBody NewPostDto newPost) {
        logger.info("Creating new post");
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.createPost(loggedUsername, newPost);
        logger.info("Post created successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getScore/{id}")
    public ResponseEntity<?> getScore(@PathVariable Long id) {
        logger.info("Getting score of post with id {}", id);
        int score = postService.getPostScore(id);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    @GetMapping("/getPosts")
    public ResponseEntity<?> getAllPosts() {
        logger.info("Getting all posts");
        List<PublicPostDto> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/getPost/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        logger.info("Getting post with id {}", id);
        PublicPostDto post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @SecurityRequirement(name="bearerAuth")
    @PutMapping("/updatePostAuthor/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody String newAuthor) {
        logger.info("Updating author of post with id {}", id);
        postService.updateAuthor(id, newAuthor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SecurityRequirement(name="bearerAuth")
    @PutMapping("/updatePostTitle/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateTitle(@PathVariable Long id, @RequestBody String newTitle) {
        logger.info("Updating title of post with id {}", id);
        postService.updateTitle(id, newTitle);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SecurityRequirement(name="bearerAuth")
    @PutMapping("/updatePostContent/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateContent(@PathVariable Long id, @RequestBody String newContent) {
        logger.info("Updating content of post with id {}", id);
        postService.updateContent(id, newContent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        logger.info("Deleting post with id {}", id);
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.deletePost(id, loggedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
