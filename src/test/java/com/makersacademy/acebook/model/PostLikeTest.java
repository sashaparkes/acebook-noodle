package com.makersacademy.acebook.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class PostLikeTest {

    private PostLike postLike = new PostLike(1L, 2L);

    @Test
    public void postLikeHasUserId() {assertThat(postLike.getUserId(), equalTo(1L));};

    @Test
    public void postLikeHasPostID() {assertThat(postLike.getPostId(), equalTo(2L));};
}
