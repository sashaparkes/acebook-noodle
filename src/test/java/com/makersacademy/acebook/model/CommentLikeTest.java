package com.makersacademy.acebook.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentLikeTest {

    @Test
    public void commentLikeHasUserIdandCommentId() {
        CommentLike commentLike = new CommentLike();
        commentLike.setUserId(1L);
        commentLike.setCommentId(2L);

        assertEquals(1L, commentLike.getUserId());
        assertEquals(2L, commentLike.getCommentId());
    }


}
