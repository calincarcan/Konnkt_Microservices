package com.konnkt.database.service;
import java.time.LocalDateTime;
import java.util.*;
import com.konnkt.database.dto.*;
import com.konnkt.database.entity.*;
import com.konnkt.database.exception.BadRequestException;
import com.konnkt.database.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService implements PostServiceInterface{
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ForumRepository forumRepository;

    private PostDto EntityToDto(Post post) {
        return new PostDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getUserId(),
                post.getForum().getForumId(),
                post.getScore()
        );
    }

    @Override
    public List<PostDto> findAll() {
        return postRepository.findAll()
                .stream()
                .map(this::EntityToDto)
                .toList();
    }

    @Override
    public Optional<PostDto> findById(Long id) {
        return postRepository.findById(id)
                .map(this::EntityToDto);
    }

    @Override
    public Optional<PostDto> createPost(InputPostDto inputPostDto) {
        Post post = new Post();
        Optional<User> author = userRepository.findByUsername(inputPostDto.username());
        Optional<Forum> forum = forumRepository.findById(inputPostDto.forumId());
        if (author.isEmpty() || forum.isEmpty()) {
            return Optional.empty();
        }
        post.setTitle(inputPostDto.title());
        post.setContent(inputPostDto.content());
        post.setUser(author.get());
        post.setForum(forum.get());
        post.setCreatedAt(LocalDateTime.now());
        post.setScore(0);
        try {
            postRepository.save(post);
        }
        catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(EntityToDto(post));
    }

    @Override
    public Optional<PostDto> updatePostTitle(Long id, String title) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            post.get().setTitle(title);
            try {
                postRepository.save(post.get());
            } catch (Exception e) {
                return Optional.empty();
            }
            return Optional.of(EntityToDto(post.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<PostDto> updatePostContent(Long id, String content) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            post.get().setContent(content);
            try {
                postRepository.save(post.get());
            } catch (Exception e) {
                return Optional.empty();
            }
            return Optional.of(EntityToDto(post.get()));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            try {
                postRepository.delete(post.get());
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return true;
    }
}
