package com.konnkt.database.service;

import com.konnkt.database.repository.*;
import com.konnkt.database.dto.*;
import com.konnkt.database.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceInterface{
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private CommentDto EntityToDto(Comment comment) {
        return new CommentDto(
                comment.getCommentId(),
                comment.getContent(),
                comment.getUser().getUserId(),
                comment.getPost().getPostId(),
                comment.getScore()
        );
    }

    @Override
    public List<CommentDto> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(this::EntityToDto)
                .toList();
    }

    @Override
    public Optional<CommentDto> findById(Long id) {
        return commentRepository.findById(id)
                .map(this::EntityToDto);
    }

    @Override
    public Optional<CommentDto> createComment(InputCommentDto commentDto) {
        Comment comment = new Comment();
        Optional<Post> post = postRepository.findById(commentDto.postId());
        Optional<User> user = userRepository.findByUsername(commentDto.username());
        if (user.isEmpty() || post.isEmpty()) {
            return Optional.empty();
        }
        comment.setUser(user.get());
        comment.setPost(post.get());
        comment.setScore(0);
        comment.setContent(commentDto.content());
        comment.setCreatedAt(LocalDateTime.now());
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(EntityToDto(comment));
    }

    @Override
    public Optional<CommentDto> updateComment(Long id, String content) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            Comment updatedComment = comment.get();
            updatedComment.setContent(content);
            try {
                commentRepository.save(updatedComment);
            } catch (Exception e) {
                return Optional.empty();
            }
            return Optional.of(EntityToDto(updatedComment));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            try {
                commentRepository.delete(comment.get());
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return true;
    }

}
