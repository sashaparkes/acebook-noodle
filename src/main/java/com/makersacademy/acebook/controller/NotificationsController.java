package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Notification;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    // Get notifications for current user
    @GetMapping("/notifications")
    public String index(Model model) {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String username = (String) principal.getAttributes().get("email");
        User currentUser = userRepository.findUserByUsername(username).get();

        Collection<Notification> notifications = new ArrayList<>();
        notifications.addAll(notificationRepository.findByReceivingUserIdOrderByCreatedAtDesc(2L));
        Map<Long, String> senderNames = new HashMap<>();
        for (Notification notification : notifications) {
            Optional<User> sender = userRepository.findById(notification.getSendingUserId());
            sender.ifPresent(user -> senderNames.put(notification.getId(), user.getFirstName()));
        }
        model.addAttribute("notifications", notifications);
        model.addAttribute("senderNames", senderNames);
        model.addAttribute("currentUser", currentUser);
        return "notifications/index";
    }


    // Mark notification as read, redirect to specific post page
    @PostMapping("/notifications")
    public RedirectView markAsRead(@ModelAttribute Notification notification, @RequestParam String id) {
        Long notificationId = Long.parseLong(id);
        Optional<Notification> readNotification = notificationRepository.findById(notificationId);
        if (readNotification.isPresent()) {
            Notification activeNotification = readNotification.get();
            activeNotification.setRead(true);
            notificationRepository.save(activeNotification);
            String postId = Long.toString(activeNotification.getPostId());
            return new RedirectView("/posts/" + postId);
        }
        else {
                return new RedirectView("genericErrorPage");
        }
    }
}
