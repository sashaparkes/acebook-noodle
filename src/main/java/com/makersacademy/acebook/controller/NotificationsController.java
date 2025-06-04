package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Notification;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndView;
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

    @PostMapping("/notifications/{id}")
    public RedirectView markAsRead(@ModelAttribute Notification notification, @RequestParam String id) {
        Long notificationId = Long.parseLong(id);
        Optional<Notification> readNotification = notificationRepository.findById(notificationId);
        if (readNotification.isPresent()) {
            Notification notif = readNotification.get();
            notif.set_read(true);
            notificationRepository.save(notif);
            return new RedirectView("/notifications");
        }
        else {
                return new RedirectView("genericErrorPage");
        }
    }
}
