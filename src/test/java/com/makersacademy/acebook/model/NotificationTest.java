package com.makersacademy.acebook.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class NotificationTest {

    Instant instant = Instant.now();
    Timestamp now = Timestamp.from(instant);
    private Notification notification = new Notification(1L, 2L, 3L, "commentLike", 4L, 5L, false, now);

    @Test
    public void notificationReceivingUserId() {assertThat(notification.getReceivingUserId(), equalTo(2L));}

    @Test
    public void notificationSendingUserId() {assertThat(notification.getSendingUserId(), equalTo(3L));}

    @Test
    public void notificationType() {assertThat(notification.getType(), containsString("commentLike"));}

    @Test
    public void notificationPostId() {assertThat(notification.getPostId(), equalTo(4L));}

    @Test
    public void notificationCommentId() {assertThat(notification.getCommentId(), equalTo(5L));}

    @Test
    public void notificationCreatedAt() {assertThat(notification.getCreatedAt(), equalTo(now));}
}
