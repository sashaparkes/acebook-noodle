package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
@IdClass(PostLikeId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLike {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "post_id")
    private Long postId;

}