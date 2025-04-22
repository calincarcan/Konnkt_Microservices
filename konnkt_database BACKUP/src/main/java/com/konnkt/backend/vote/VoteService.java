package com.konnkt.backend.vote;

import com.konnkt.backend.comment.Comment;
import com.konnkt.backend.comment.CommentRepository;
import com.konnkt.backend.exception.BadRequestException;
import com.konnkt.backend.exception.ForbiddenException;
import com.konnkt.backend.exception.NotFoundException;
import com.konnkt.backend.post.Post;
import com.konnkt.backend.post.PostRepository;
import com.konnkt.backend.post.PostService;
import com.konnkt.backend.user.UserRepository;
import com.konnkt.backend.vote.dto.PublicVoteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoteService {
    @Autowired
    private final PostService postService;
    @Autowired
    private final VoteRepository voteRepository;
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public VoteService(PostService postService, VoteRepository voteRepository) {
        this.postService = postService;
        this.voteRepository = voteRepository;
    }

    private PublicVoteDto voteToPublic(Vote vote) {
        PublicVoteDto publicVoteDto = new PublicVoteDto();
        publicVoteDto.setAuthor(vote.getUser().getUsername());
        if (vote.getPost() != null)
            publicVoteDto.setPostId(vote.getPost().getPostId());
        else
            publicVoteDto.setCommentId(vote.getComment().getCommentId());
        publicVoteDto.setVoteType(vote.getVoteType());
        return publicVoteDto;
    }

    public List<PublicVoteDto> getAllVotes() {
        return voteRepository.findAll().stream().map(this::voteToPublic).toList();
    }

    public PublicVoteDto getVoteById(Long id) {
        Vote vote = voteRepository.findById(id).orElseThrow(() -> new BadRequestException("Vote not found"));
        return voteToPublic(vote);
    }

    public void createVote(String username, PublicVoteDto vote) {
        Vote newVote = new Vote();
        newVote.setVoteType(vote.getVoteType());
//        username always exists
        newVote.setUser(userRepository.findUserByUsername(username).orElseThrow(() -> new BadRequestException("User not found")));
//        vote.author is not needed
        if (vote.getCommentId() != null) {
            Comment comment = commentRepository.findById(vote.getCommentId()).orElseThrow(() -> new BadRequestException("Comment not found"));
            newVote.setComment(comment);
            comment.setScore(comment.getScore() + vote.getVoteType());
            commentRepository.save(comment);
        }
        if (vote.getPostId() != null) {
            Post post = postRepository.findById(vote.getPostId()).orElseThrow(() -> new BadRequestException("Post not found"));
            newVote.setPost(post);
            post.setScore(post.getScore() + vote.getVoteType());
            postRepository.save(post);
        }
        newVote.setCreatedAt(LocalDateTime.now());
        voteRepository.save(newVote);
    }

    public void deleteVote(Long id) {
        Vote vote = voteRepository.findById(id).orElseThrow(() -> new BadRequestException("Vote not found"));
        if (vote.getPost() != null) {
            Post post = vote.getPost();
            post.setScore(post.getScore() - vote.getVoteType());
            postRepository.save(post);
        } else {
            Comment comment = vote.getComment();
            comment.setScore(comment.getScore() - vote.getVoteType());
            commentRepository.save(comment);
        }
        voteRepository.delete(vote);
    }

    public void deleteVote(Long id, String loggedUsername) {
        Vote vote = voteRepository.findById(id).orElseThrow(() -> new NotFoundException("Vote not found"));
        if (!vote.getUser().getUsername().equals(loggedUsername) &&
            !userRepository.findUserByUsername(loggedUsername).orElseThrow(() -> new NotFoundException("User not found"))
                .getRole().getRoleName().equals("admin")) {
            throw new ForbiddenException("You are not authorized to delete this vote");
        }
        voteRepository.delete(vote);
    }

    public void changeVote(Long id) {
        Vote vote = voteRepository.findById(id).orElseThrow(() -> new BadRequestException("Vote not found"));
        if (vote.getPost() != null) {
            Post post = vote.getPost();
            post.setScore(post.getScore() - 2 * vote.getVoteType());
            postRepository.save(post);
        } else {
            Comment comment = vote.getComment();
            comment.setScore(comment.getScore() - 2 * vote.getVoteType());
            commentRepository.save(comment);
        }
        vote.setVoteType(vote.getVoteType() * -1);
        voteRepository.save(vote);
    }

    public void changeVote(Long id, String loggedUsername) {
        Vote vote = voteRepository.findById(id).orElseThrow(() -> new NotFoundException("Vote not found"));
        if (!vote.getUser().getUsername().equals(loggedUsername) &&
            !userRepository.findUserByUsername(loggedUsername).orElseThrow(() -> new NotFoundException("User not found"))
                .getRole().getRoleName().equals("admin")) {
            throw new ForbiddenException("You are not authorized to change this vote");
        }
        if (vote.getPost() != null) {
            Post post = vote.getPost();
            post.setScore(post.getScore() - 2 * vote.getVoteType());
            postRepository.save(post);
        } else {
            Comment comment = vote.getComment();
            comment.setScore(comment.getScore() - 2 * vote.getVoteType());
            commentRepository.save(comment);
        }
        vote.setVoteType(vote.getVoteType() * -1);
        voteRepository.save(vote);
    }
}
