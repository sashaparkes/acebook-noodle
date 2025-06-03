package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Notification;
import com.makersacademy.acebook.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class NotificationsController {

    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping("/notifications")
    public ModelAndView notifications() {
        ModelAndView modelAndView = new ModelAndView("/notifications");
        Iterable<Notification> notifications = notificationRepository.findAll();

        modelAndView.addObject("notifications", notifications);
        return modelAndView;
    }

    @PostMapping("/notifications")
    public RedirectView create(Notification notification) {
        notificationRepository.save(notification);
        return new RedirectView("/post/{notification.post_id}");
    }

}
