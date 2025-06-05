package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.sql.Timestamp;

// Composite key class
@Data
@NoArgsConstructor
@AllArgsConstructor
class FriendId implements Serializable {
    private Long mainUserId;
    private Long friendUserId;
}

@Data
@Entity
@Table(name = "friends")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FriendId.class)
public class Friend {

    @Id
    @Column(name = "main_user_id")
    private Long mainUserId;

    @Id
    @Column(name = "friend_user_id")
    private Long friendUserId;

    @Column(name = "friends_since")
    private Timestamp friendsSince;
}