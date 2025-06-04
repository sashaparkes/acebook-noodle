package com.makersacademy.acebook.model;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.makersacademy.acebook.repository.FriendRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;


public class FriendRequestTest {

    @Autowired
    FriendRequestRepository repository;

    @Test
    public void friendRequestHasContent() {
        FriendRequest request = new FriendRequest(null, 1L, 2L, "unapproved", new Timestamp(System.currentTimeMillis()), null);
        repository.save(request);
        assertThat(request.getStatus(), containsString("unapproved"));
    }
}
