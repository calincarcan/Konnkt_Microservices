package com.konnkt.backend.post;

import com.konnkt.backend.exception.BadRequestException;
import com.konnkt.backend.exception.ForbiddenException;
import com.konnkt.backend.exception.NotFoundException;
import com.konnkt.backend.forum.Forum;
import com.konnkt.backend.forum.ForumRepository;
import com.konnkt.backend.post.dto.NewPostDto;
import com.konnkt.backend.post.dto.PublicPostDto;
import com.konnkt.backend.role.RoleService;
import com.konnkt.backend.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);
    @Autowired
    private ForumRepository forumRepository;

    public int getPostScore(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new BadRequestException("Post not found"));
        return post.getScore();
    }

    public PublicPostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new BadRequestException("Post not found"));
        PublicPostDto publicPostDto = new PublicPostDto();
        publicPostDto.setAuthor(post.getUser().getUsername());
        publicPostDto.setTitle(post.getTitle());
        publicPostDto.setContent(post.getContent());
        return publicPostDto;
    }

    public List<PublicPostDto> getAllPosts() {
        return postRepository.findAll().stream().map(post -> {
            PublicPostDto publicPostDto = new PublicPostDto();
            publicPostDto.setAuthor(post.getUser().getUsername());
            publicPostDto.setTitle(post.getTitle());
            publicPostDto.setContent(post.getContent());
            return publicPostDto;
        }).toList();
    }

    public void createPost(String username, NewPostDto newPost) {
        if (newPost.getForum() == null)
            newPost.setForum("General");
        Forum forum = forumRepository.findByName(newPost.getForum()).orElse(forumRepository.findByName("General").get());
        Post post = new Post();
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
        post.setUser(userRepository.findUserByUsername(username).orElseThrow());
        post.setForum(forum);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
    }

    public void updateAuthor(Long id, String newAuthor) {
        Post post = postRepository.findById(id).orElseThrow(() -> new BadRequestException("Post not found"));
        post.setUser(userRepository.findUserByUsername(newAuthor).orElseThrow(() -> new BadRequestException("User not found")));
        postRepository.save(post);
    }

    public void updateTitle(Long id, String newTitle) {
        Post post = postRepository.findById(id).orElseThrow(() -> new BadRequestException("Post not found"));
        post.setTitle(newTitle);
        postRepository.save(post);
    }

    public void updateContent(Long id, String newContent) {
        Post post = postRepository.findById(id).orElseThrow(() -> new BadRequestException("Post not found"));
        post.setContent(newContent);
        postRepository.save(post);
    }

    public void deletePost(Long id, String loggedUsername) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));
        if (!post.getUser().getUsername().equals(loggedUsername) &&
            !userRepository.findUserByUsername(loggedUsername).orElseThrow(() -> new NotFoundException("User not found"))
                .getRole().getRoleName().equals("admin")) {
            throw new ForbiddenException("You are not authorized to delete this post");
        }
        postRepository.delete(post);
    }
}
