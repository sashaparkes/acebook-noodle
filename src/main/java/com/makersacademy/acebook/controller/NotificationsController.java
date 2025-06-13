package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.Notification;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class NotificationsController {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationService notificationService;


    // Get notifications for current user
    @GetMapping("/notifications")
    public String index(Model notificationsPage, @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        User currentUser = userRepository.findUserByUsername(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer notificationCount = notificationService.notificationCount(currentUser.getId());

        Collection<Notification> notifications = notificationService.getNotifications(currentUser.getId());
        Map<Long, String> senderNames = notificationService.getNotificationSenderNames(notifications);
        List<User> pendingFriendRequests = notificationService.getFriendRequests(currentUser.getId());

        notificationsPage.addAttribute("pendingFriendRequests", pendingFriendRequests);
        notificationsPage.addAttribute("notifications", notifications);
        notificationsPage.addAttribute("senderNames", senderNames);
        notificationsPage.addAttribute("currentUser", currentUser);
        return "notifications/index";
    }


    // Mark notification as read, redirect to specific post page
    @PostMapping("/notifications")
    public RedirectView markAsRead(@ModelAttribute Notification notification, @RequestParam String id) {
        Long notificationId = Long.parseLong(id);
        String postId = notificationService.readNotification(notificationId);
        return new RedirectView("/posts/" + postId);
    }
}
