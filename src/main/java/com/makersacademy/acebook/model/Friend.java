package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

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
}