package com.makersacademy.acebook.model;


import jakarta.persistence.*;
import lombok.*;
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
    private Long receiving_user_id;
    private Long sending_user_id;
    private String type;
    private Long post_id;
    private boolean is_read;
    private Timestamp created_at;

}
