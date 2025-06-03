package com.makersacademy.acebook.model;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "NOTIFICATIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer receiving_user_id;
    private Integer sending_user_id;
    private String type;
    private Integer post_id;
    private boolean is_read;
    private Timestamp created_at;

}
