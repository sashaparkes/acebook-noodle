package com.makersacademy.acebook.model;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class PostTest {

	Timestamp now = new Timestamp();
	private Post post = new Post("hello", 1, );


	@Test
	public void postHasContent() {
		assertThat(post.getContent(), containsString("hello"));
	}

}
