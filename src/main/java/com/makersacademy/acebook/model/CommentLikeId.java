package com.makersacademy.acebook.model;

import java.io.Serializable;

public class CommentLikeId implements Serializable {
    private Long userId;
    private Long commentId;

    public CommentLikeId(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }

}
