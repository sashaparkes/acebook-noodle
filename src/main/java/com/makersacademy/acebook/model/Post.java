package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "POSTS")
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name="user_id")
    private Long userId; // current user is populated in PostController

    @CreationTimestamp
    @Column(name="time_posted")
    private Timestamp timePosted;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;


    public Post(Long id) {
        this.id = id;
    }
}
