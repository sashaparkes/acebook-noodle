package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;

import static java.lang.Boolean.TRUE;

@Data
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private boolean enabled;

    // No-arg constructor (required by JPA)
    public User() {
        this.enabled = TRUE;
    }

    // Constructor with ID only — for setting references (e.g., CommentLike)
    public User(Long userId) {
        this.id = userId;
        this.enabled = TRUE;
    }

    public User(String username) {
        this.username = username;
        this.enabled = TRUE;
    }

    public User(String username, boolean enabled) {
        this.username = username;
        this.enabled = enabled;
    }
}