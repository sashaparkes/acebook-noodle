package com.makersacademy.acebook.model;


import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class FriendRequestTest {

    Instant instant = Instant.now();
    Timestamp now = Timestamp.from(instant);
    FriendRequest request = new FriendRequest(null, 1L, 2L, "pending", now, null);

    @Test
    public void friendRequestHasRequesterID() {assertThat(request.getRequesterId(), equalTo(1L));}

    @Test
    public void friendRequestHasReceiverId() {assertThat(request.getReceiverId(), equalTo(2L));}

    @Test
    public void friendRequestHasStatus() {assertThat(request.getStatus(), containsString("pending"));}

    @Test
    public void friendRequestHasCreatedAt() {assertThat(request.getCreatedAt(), equalTo(now));}
}
