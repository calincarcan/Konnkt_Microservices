package com.konnkt.backend.comment;

import com.konnkt.backend.comment.dto.PublicCommentDto;
import com.konnkt.backend.exception.BadRequestException;
import com.konnkt.backend.post.Post;
import com.konnkt.backend.post.PostRepository;
import com.konnkt.backend.user.User;
import com.konnkt.backend.user.UserRepository;
import com.konnkt.backend.vote.Vote;
import com.konnkt.backend.vote.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private VoteRepository voteRepository;

    private PublicCommentDto commentToPublic(Comment comment) {
        PublicCommentDto publicCommentDto = new PublicCommentDto();
        publicCommentDto.setAuthor(comment.getUser().getUsername());
        publicCommentDto.setContent(comment.getContent());
        return publicCommentDto;
    }

    public List<PublicCommentDto> getAllComments() {
        return commentRepository.findAll().stream().map(this::commentToPublic).toList();
    }

    public PublicCommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new BadRequestException("Comment not found"));
        return commentToPublic(comment);
    }

    public void createComment(String loggedUserName, Long postId, String comment) {
        User user = userRepository.findUserByUsername(loggedUserName).orElseThrow(() -> new BadRequestException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException("Post not found"));
        Comment newComment = new Comment();
        newComment.setUser(user);
        newComment.setContent(comment);
        newComment.setCreatedAt(LocalDateTime.now());
        newComment.setScore(0);
        newComment.setPost(post);
        commentRepository.save(newComment);
    }

    public void changeContent(Long id, String content) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new BadRequestException("Comment not found"));
        comment.setContent(content);
        commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new BadRequestException("Comment not found"));
        commentRepository.delete(comment);
    }

    public void deleteComment(Long id, String loggedUsername) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new BadRequestException("Comment not found"));
        if (!comment.getUser().getUsername().equals(loggedUsername) &&
            !userRepository.findUserByUsername(loggedUsername).orElseThrow(() -> new BadRequestException("User not found"))
                .getRole().getRoleName().equals("admin")) {
            throw new BadRequestException("You are not authorized to delete this comment");
        }
        commentRepository.delete(comment);
    }

    public int getCommentScore(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new BadRequestException("Comment not found"));
        return comment.getScore();
    }
}
