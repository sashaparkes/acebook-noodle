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

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationService notificationService;

    // Handle ALL generic exceptions
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, HttpServletRequest request, Model model) {
        populateModel(model, request);
        model.addAttribute("errorMessage", ex.getMessage());
        System.out.println("GlobalExceptionHandler caught general exception: " + ex);
        return "genericErrorPage";
    }

    // Handle missing post / missing resource exceptions (404-like behavior)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFoundException(NoSuchElementException ex, HttpServletRequest request, Model model) {
        populateModel(model, request);
        model.addAttribute("errorMessage", "The resource you requested was not found.");
        System.out.println("GlobalExceptionHandler caught NoSuchElementException: " + ex);
        return "genericErrorPage";
    }

    // Extracted reusable method for populating navbar model attributes
    private void populateModel(Model model, HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof DefaultOidcUser principal) {
                String username = (String) principal.getAttributes().get("email");
                User user = userRepository.findUserByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                model.addAttribute("user", user);
                Integer notificationCount = notificationService.notificationCount(user.getId());
                model.addAttribute("notificationCount", notificationCount);
                model.addAttribute("globalWall", true);
            }
        } catch (Exception e) {
            // Fails silently if no authentication (e.g. not logged in)
        }
        model.addAttribute("requestUri", request.getRequestURI());
    }
}
