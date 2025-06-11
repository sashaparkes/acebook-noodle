package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.*;
import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import jakarta.annotation.Nullable;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;


@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    FriendRequestRepository friendRequestRepository;
    @Autowired
    UserRepository userRepository;


    // Creates a new notification for user, when someone comments on or likes their post or comment.
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


    // Gets count of all unread notifications and pending friend requests for current user
    public Integer notificationCount(Long receivingUserId) {
        Integer pendingRequests = friendRequestRepository.countByReceiverIdAndStatus(receivingUserId, "pending");
        Integer notificationsCount = notificationRepository.countByReceivingUserIdAndIsRead(receivingUserId, false);
        Integer totalNotificationsCount = pendingRequests + notificationsCount;
        return totalNotificationsCount;
    }

    // Gets list of all notifications for current user, sorted by the newest ones first
    public Collection<Notification> getNotifications(Long userId) {
        Collection<Notification> notifications = new ArrayList<>();
        notifications.addAll(notificationRepository.findByReceivingUserIdOrderByCreatedAtDesc(userId));
        return notifications;
    }

    // Gets list of notification senders, used in conjunction with getNotifications for formatting purposes.
    public Map<Long, String> getNotificationSenderNames(Collection<Notification> notifications) {
        Map<Long, String> senderNames = new HashMap<>();
        for (Notification notification : notifications) {
            Optional<User> sender = userRepository.findById(notification.getSendingUserId());
            sender.ifPresent(user -> senderNames.put(notification.getId(), user.getFirstName()));
        }
        return senderNames;
    }

    // Mark notification as read, redirect to specific post page
    public String readNotification(Long notificationId) {
        Notification activeNotification = notificationRepository.findById(notificationId).orElse(null);
        if (activeNotification == null) {
            return null;
        }
        activeNotification.setRead(true);
        notificationRepository.save(activeNotification);
        return Long.toString(activeNotification.getPostId());
    }

    // Gets list of pending friend requests, orders by newest first.
    public List <User> getFriendRequests(Long currentUserId) {
        List<FriendRequest> pendingFriendRequests = friendRequestRepository.findAllByReceiverIdAndStatusOrderByCreatedAtDesc(currentUserId, "pending");
        List<User> requesterUsers = new ArrayList<>();
        for (FriendRequest request : pendingFriendRequests) {
            Long requesterId = request.getRequesterId();
            User requesterUser = userRepository.findById(requesterId).orElse(null);
            requesterUsers.add(requesterUser);
        }
        return requesterUsers;
    }
}

