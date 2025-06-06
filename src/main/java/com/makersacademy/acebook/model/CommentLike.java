package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment_likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "comment_id"}))
@IdClass(CommentLikeId.class)
@Getter
@Setter
@NoArgsConstructor
public class CommentLike {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "comment_id")
    private Long commentId;


}
