package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "profile_pic")
    private String profilePic;

    public User(){
    }

    public User(String username) {
        this.enabled = TRUE;
    }

    public User(String username, String firstName, String last_name, String profilePic) {
        this.username = username;
        this.enabled = TRUE;
        this.firstName = firstName;
        this.lastName = last_name;
        this.profilePic = profilePic;
    }

    public User(String username, boolean enabled, String firstName, String last_name, String profilePic) {
        this.username = username;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = last_name;
        this.profilePic = profilePic;
    }

    public User(Long userId) {
    }
}