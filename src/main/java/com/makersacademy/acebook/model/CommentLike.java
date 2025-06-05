//package com.makersacademy.acebook.model;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "comment_likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "comment_id"}))
//@Getter
//@Setter
//@IdClass(CommentLikeId.class)
//@NoArgsConstructor
//public class CommentLike {
//
//
//
//    @Id
//
//    @JoinColumn(name = "user_id", nullable = false)
//    private Long userId;
//
//    @Id
//
//    @JoinColumn(name = "comment_id", nullable = false)
//    private Long commentId;
//
//}
