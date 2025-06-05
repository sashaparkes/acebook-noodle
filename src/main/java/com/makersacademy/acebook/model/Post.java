package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

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

}
