package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Controller
public class PostsController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageStorageService imageStorageService;

    @Value("${file.upload-dir.post-images}")
    private String uploadDir;

    // View all posts
    @GetMapping("/posts")
    public String index(Model model) {
        Iterable<Post> posts = postRepository.findByOrderByTimePostedDesc();
        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());

        // Creates principal variable of type Default0idcUser - Spring Security class which represents a user authenticated by Auth0
        // The get commands work together to extract user attributes from Auth0 (email, given_name, family_name)
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // Uses the pricipal variable above to extract email and assign to username var
        // Then utilises the 'username' variable to search the database and return matching User object
        // Adds the user object to the model (page)
        String username = (String) principal.getAttributes().get("email");
        User user = userRepository.findUserByUsername(username).get();
        model.addAttribute("user", user);

        return "posts/index";
    }


    // Create new post
    @PostMapping("/posts")
    public RedirectView create(
            @RequestParam("content") String content,
            @AuthenticationPrincipal(expression = "attributes['email']") String email,
            @RequestParam("image") MultipartFile file
    ) throws IOException {
        Optional<User> user = userRepository.findUserByUsername(email);
        if (user.isPresent()) {
            Post post = new Post();
            post.setContent(content);
            post.setUserId(user.get().getId());

            postRepository.save(post);

            String fileName = imageStorageService.storePostImage(file, String.valueOf(post.getId()));
            if (fileName != null) {
                post.setImage(fileName);
                postRepository.save(post);
            }

        }
        return new RedirectView("/posts");
    }


    //View specific post
    @GetMapping("/posts/{id}")
    public ModelAndView viewPost(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("posts/post");
        ModelAndView errorView = new ModelAndView("genericErrorPage");
        Optional<Post> currentPost = postRepository.findById(id);
        if (currentPost.isPresent()) {
            Post post = currentPost.get();
            modelAndView.addObject("post", post);
            return modelAndView;
        }
        else {
            return errorView;
        }
    }
}
