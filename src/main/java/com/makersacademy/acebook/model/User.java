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

    // For some reason, it wasn't pulling through first name and last name for post comments
    // Until I linked it to the DB like below and then used Java's preferred camelCase
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    //
    private String profile_pic;

    public User(){
    }

    public User(String username) {
        this.enabled = TRUE;
    }

    public User(String username, String first_name, String last_name, String profile_pic) {
        this.username = username;
        this.enabled = TRUE;
        this.firstName = first_name;
        this.lastName = last_name;
        this.profile_pic = profile_pic;
    }

    public User(String username, boolean enabled, String first_name, String last_name, String profile_pic) {
        this.username = username;
        this.enabled = enabled;
        this.firstName = first_name;
        this.lastName = last_name;
        this.profile_pic = profile_pic;
    }

    public User(Long userId) {
    }
}