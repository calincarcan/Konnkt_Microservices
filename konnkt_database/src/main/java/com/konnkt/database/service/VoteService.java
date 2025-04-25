package com.konnkt.database.service;

import com.konnkt.database.dto.*;
import com.konnkt.database.entity.*;
import com.konnkt.database.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VoteService implements VoteServiceInterface {
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private VoteDto EntityToDto(Vote vote) {
        if (vote.getPost() == null)
            return new VoteDto(
                    vote.getVoteId(),
                    vote.getUser().getUserId(),
                    "comment",
                    vote.getComment().getCommentId(),
                    vote.getVoteType()
            );
        return new VoteDto(
                vote.getVoteId(),
                vote.getUser().getUserId(),
                "post",
                vote.getPost().getPostId(),
                vote.getVoteType()
        );
    }

    @Override
    public List<VoteDto> findAll() {
        return voteRepository.findAll()
                .stream()
                .map(this::EntityToDto)
                .toList();
    }

    @Override
    public Optional<VoteDto> findById(Long id) {
        return voteRepository.findById(id)
                .map(this::EntityToDto);
    }

    @Override
    public Optional<VoteDto> createVote(InputVoteDto inputVoteDto) {
        Vote vote = new Vote();
        Optional<User> user = userRepository.findByUsername(inputVoteDto.username());
        Optional<Post> post = postRepository.findById(inputVoteDto.parentId());
        Optional<Comment> comment = commentRepository.findById(inputVoteDto.parentId());
        if (inputVoteDto.parentType().equals("post")) {
            if (post.isEmpty()) {
                return Optional.empty();
            }
            vote.setPost(post.get());
            int score = post.get().getScore();
            post.get().setScore(score + inputVoteDto.voteType());
            try {
                postRepository.save(post.get());
            } catch (Exception e) {
                return Optional.empty();
            }
            vote.setComment(null);
        } else if (inputVoteDto.parentType().equals("comment")) {
            if (comment.isEmpty()) {
                return Optional.empty();
            }
            vote.setComment(comment.get());
            int score = comment.get().getScore();
            comment.get().setScore(score + inputVoteDto.voteType());
            try {
                commentRepository.save(comment.get());
            } catch (Exception e) {
                return Optional.empty();
            }
            vote.setPost(null);
        } else {
            return Optional.empty();
        }
        if (user.isEmpty()) {
            return Optional.empty();
        }
        vote.setCreatedAt(LocalDateTime.now());
        vote.setUser(user.get());
        vote.setVoteType(inputVoteDto.voteType());
        try {
            voteRepository.save(vote);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(EntityToDto(vote));
    }

    @Override
    public Optional<VoteDto> changeVote(Long id) {
        Optional<Vote> vote = voteRepository.findById(id);
        if (vote.isPresent()) {
            Vote updatedVote = vote.get();
            updatedVote.setVoteType(vote.get().getVoteType() * -1);
            try {
                voteRepository.save(updatedVote);
            } catch (Exception e) {
                return Optional.empty();
            }
            if (updatedVote.getPost() != null) {
                Post post = updatedVote.getPost();
                post.setScore(post.getScore() + 2 * updatedVote.getVoteType());
                try {
                    postRepository.save(post);
                } catch (Exception e) {
                    return Optional.empty();
                }
            } else if (updatedVote.getComment() != null) {
                Comment comment = updatedVote.getComment();
                comment.setScore(comment.getScore() + 2 * updatedVote.getVoteType());
                try {
                    commentRepository.save(comment);
                } catch (Exception e) {
                    return Optional.empty();
                }
            }
            return Optional.of(EntityToDto(updatedVote));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteVote(Long id) {
        Optional<Vote> vote = voteRepository.findById(id);
        if (vote.isPresent()) {
            if (vote.get().getPost() != null) {
                Post post = vote.get().getPost();
                post.setScore(post.getScore() - vote.get().getVoteType());
                try {
                    postRepository.save(post);
                } catch (Exception e) {
                    return false;
                }
            } else if (vote.get().getComment() != null) {
                Comment comment = vote.get().getComment();
                comment.setScore(comment.getScore() - vote.get().getVoteType());
                try {
                    commentRepository.save(comment);
                } catch (Exception e) {
                    return false;
                }
            }
            try {
                voteRepository.delete(vote.get());
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return true;
    }

}
