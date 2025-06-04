package com.makersacademy.acebook.model;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

public class PostTest {

    Instant instant = Instant.now();
    Timestamp now = Timestamp.from(instant);

	private Post post = new Post(null, "hello", 1L, now);

	@Test
	public void postHasContent() {
		assertThat(post.getContent(), containsString("hello"));
	}

}
