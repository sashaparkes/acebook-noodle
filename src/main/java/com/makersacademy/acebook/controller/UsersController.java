package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

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

    @GetMapping("/settings")
    public ModelAndView settings(){
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String username = (String) principal.getAttributes().get("email");
        User userByEmail = userRepository.findUserByUsername(username).get();
//        Optional<User> user = userRepository.findById(userByEmail.getId());
        ModelAndView settings = new ModelAndView("/settings");
        settings.addObject("user", userByEmail);
        return settings;
    }

//    @PostMapping("/settings")
//    public RedirectView update(@ModelAttribute("user") User user) {
//        userRepository.save(user);
//        return new RedirectView("/settings");
//    }

//    @PostMapping("/settings")
//    public RedirectView update(@ModelAttribute User userFromForm, @RequestParam("file") MultipartFile file) {
//        // Get the logged-in user's email
//        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//
//        String email = (String) principal.getAttributes().get("email");
//
//        // Find the user from the DB
//        User userInDb = userRepository.findUserByUsername(email)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        try {
//            String filePath = saveImage(file);
//            userInDb.setProfile_pic(filePath);
//        } catch (IOException e) {
//            return new RedirectView("/settings");
//        }
//
//        // Update only allowed fields
//        userInDb.setFirst_name(userFromForm.getFirst_name());
//        userInDb.setLast_name(userFromForm.getLast_name());
//        // Add others if needed: userInDb.setLastName(...), etc.
//
//        // Save the updated user
//        userRepository.save(userInDb);
//
//        return new RedirectView("/settings");
//    }

    @PostMapping("/settings")
    public RedirectView update(@ModelAttribute User userFromForm, @RequestParam("file") MultipartFile file) {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String email = (String) principal.getAttributes().get("email");

        User userInDb = userRepository.findUserByUsername(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                saveImage(file); // Save to disk
                userInDb.setProfile_pic("/images/user_profile/" + fileName); // Or adjust path
            }
        } catch (IOException e) {
            e.printStackTrace(); // optionally log it
            return new RedirectView("/settings?error=file");
        }

        userInDb.setFirst_name(userFromForm.getFirst_name());
        userInDb.setLast_name(userFromForm.getLast_name());

        userRepository.save(userInDb);

        return new RedirectView("/settings");
    }


    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }


}
