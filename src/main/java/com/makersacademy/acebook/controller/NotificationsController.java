package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Notification;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/notifications")
    public String index(Model model) {
        List<Notification> notifications = new ArrayList<>();
        notificationRepository.findAll().forEach(notifications::add);
        Collections.reverse(notifications);
        Map<Long, String> senderNames = new HashMap<>();
        for (Notification notification : notifications) {
            Optional<User> sender = userRepository.findById(notification.getSending_user_id());
            sender.ifPresent(user -> senderNames.put(notification.getId(), user.getUsername())); //needs updated with proper User getFirstName
        }
        model.addAttribute("notifications", notifications);
        model.addAttribute("senderNames", senderNames);
        return "notifications/index";
    }
//
//    @PostMapping("/notifications")
//    public RedirectView markAsRead(@ModelAttribute Notification notification) {
//        Optional<Notification> readNotification = notificationRepository.findById(1L);
//        if (readNotification.isPresent()) {
//            Notification notif = readNotification.get();
//            notif.set_read(true);
//            notificationRepository.save(notif);
//        }
////        notification.set_read(true);
//        return new RedirectView("/notifications");
//    }
}
