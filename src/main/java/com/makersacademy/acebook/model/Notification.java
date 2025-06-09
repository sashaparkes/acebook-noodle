package com.makersacademy.acebook.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Data
@Entity(name = "Notification")
@Table(name = "NOTIFICATIONS")
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="receiving_user_id")
    private Long receivingUserId;

    @Column(name="sending_user_id")
    private Long sendingUserId;

    private String type;

    @Column(name= "post_id")
    private Long postId;

    @Column(name="comment_id")
    private Long commentId;

    @Column(name="is_read")
    private boolean isRead;

    @CreationTimestamp
    @Column(name="created_at")
    private Timestamp createdAt;

}
