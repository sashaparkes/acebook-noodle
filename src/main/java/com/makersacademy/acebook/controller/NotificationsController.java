package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Notification;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class NotificationsController {

    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping("/notifications")
    public String index(Model model) {
        Iterable<Notification> notifications = notificationRepository.findAll();
        model.addAttribute("notifications", notifications);
        model.addAttribute("notifications", new Notification());
        return "notifications/index";
    }

}
