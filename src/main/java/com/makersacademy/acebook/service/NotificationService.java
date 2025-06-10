package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.*;
import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Nullable;


@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;


    public void newNotification(Long userId, String type, @Nullable Comment comment, @Nullable PostLike postLike, @Nullable CommentLike commentLike) {
        Post post = null;
        Long commentId = null;

        if (type.equals("comment") && comment != null) {
            commentId = comment.getId();
            post = comment.getPost();
        } else if (type.contains("Like")) { // && (postLike != null || commentLike != null)
            if (commentLike != null) {
                commentId = commentLike.getCommentId();
                post = commentRepository.findById(commentId).get().getPost();
            } else if (postLike != null) {
                post = postRepository.findById(postLike.getPostId()).get();
            }
        }
        if (post != null && !(userId.equals(post.getUserId()))) {
            Notification notification = new Notification(null, post.getUserId(), userId, type, post.getId(), commentId, false, null);
            notificationRepository.save(notification);
        }
    }


    public Integer notificationCount(Long receivingUserId) {
        return notificationRepository.countByReceivingUserIdAndIsRead(receivingUserId, false);
    }
}

