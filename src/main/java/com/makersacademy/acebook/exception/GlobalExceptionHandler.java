package com.makersacademy.acebook.exception;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // This stuff keeps the navbar user/notification elements working

    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationService notificationService;

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, HttpServletRequest request, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof DefaultOidcUser) {
                DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
                String username = (String) principal.getAttributes().get("email");
                User user = userRepository.findUserByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                model.addAttribute("user", user);
                Integer notificationCount = null;
                model.addAttribute("notificationCount", notificationCount);
                notificationCount = notificationService.notificationCount(user.getId());
                Boolean globalWall = true;
            }
        } catch (Exception e) {
            // This should (hopefully) catch all errors
        }

        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("requestUri", request.getRequestURI());
        System.out.println("Global exception handler caught: " + ex);
        return "genericErrorPage";
    }
}