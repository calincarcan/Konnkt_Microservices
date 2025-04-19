package com.konnkt.backend.vote;

import com.konnkt.backend.comment.Comment;
import com.konnkt.backend.post.Post;
import com.konnkt.backend.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "votes")
@Getter @Setter
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(nullable = false)
    private int voteType; // 1 for like, -1 for dislike

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
