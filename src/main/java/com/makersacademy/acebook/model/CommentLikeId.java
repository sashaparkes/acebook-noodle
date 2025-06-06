package com.makersacademy.acebook.model;

import java.io.Serializable;
import java.util.Objects;

public class CommentLikeId implements Serializable {

    private Long userId;
    private Long commentId;

    public CommentLikeId() {}

    public CommentLikeId(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentLikeId)) return false;
        CommentLikeId that = (CommentLikeId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, commentId);
    }
}
