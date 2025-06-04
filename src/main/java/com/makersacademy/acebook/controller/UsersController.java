package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users/after-login")
    public RedirectView afterLogin() {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String username = (String) principal.getAttributes().get("email");
        String first_name = (String) principal.getAttributes().get("given_name");
        String last_name = (String) principal.getAttributes().get("family_name");
        String profile_pic = "/images/profile/default.jpg";
        userRepository
                .findUserByUsername(username)
                .orElseGet(() -> userRepository.save(new User(username, first_name, last_name, profile_pic)));

        return new RedirectView("/posts");
    }

//    @GetMapping("settings/{id}")
//    public ModelAndView userSettings(@PathVariable("id"), Long id){
//
//    }
}
