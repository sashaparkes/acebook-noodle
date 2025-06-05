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
    private String first_name;
    private String last_name;
    private String profile_pic;

    public User(){
    }

    public User(String username) {
        this.enabled = TRUE;
    }

    public User(String username, String first_name, String last_name, String profile_pic) {
        this.username = username;
        this.enabled = TRUE;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_pic = profile_pic;
    }

    public User(String username, boolean enabled, String first_name, String last_name, String profile_pic) {
        this.username = username;
        this.enabled = enabled;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_pic = profile_pic;
    }

    public User(Long userId) {
    }
}