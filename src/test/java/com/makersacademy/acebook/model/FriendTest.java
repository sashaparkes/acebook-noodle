package com.makersacademy.acebook.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class FriendTest {

    Instant instant = Instant.now();
    Timestamp now = Timestamp.from(instant);

    private Friend friend = new Friend(1L, 2L, now);

    @Test
    public void friendHasMainUserId() {assertThat(friend.getMainUserId(), equalTo(1L));};

    @Test
    public void friendHasFriendUserId() {assertThat(friend.getFriendUserId(), equalTo(2L));};

    @Test
    public void friendHasFriendsSince() {assertThat(friend.getFriendsSince(), equalTo(now));};
}
