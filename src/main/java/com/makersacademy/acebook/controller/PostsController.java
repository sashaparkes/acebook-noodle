package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.dto.CommentDto;
import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.CommentLikeService;
import com.makersacademy.acebook.service.CommentService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class PostsController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

// Shanni Code
private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    public PostsController(PostRepository postRepository,
                           UserRepository userRepository,
                           CommentService commentService,
                           CommentLikeService commentLikeService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentService = commentService;
        this.commentLikeService = commentLikeService;
    }
//    end shanni code


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
    public RedirectView create(@ModelAttribute Post post, @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Optional<User> user = userRepository.findUserByUsername(email);
        if (user.isPresent()) {
            post.setUserId(user.get().getId());
            postRepository.save(post);
        }
        return new RedirectView("/posts");
    }


    //View specific post
    @GetMapping("/posts/{id}")
    public ModelAndView viewPost(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("posts/post");
        ModelAndView errorView = new ModelAndView("genericErrorPage");
//check from here
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

//    Shanni code
//Optional<Post> currentPost = postRepository.findById(id);
//        if (currentPost.isEmpty()) {
//        return errorView;
//    }
//
//    Post post = currentPost.get();
//    List<Comment> commentEntities = commentService.getCommentsForPost(id);
//
//    List<CommentDto> commentDtos = commentEntities.stream()
//            .map(comment -> {
//                String displayName = comment.getUser().getFirstName() + " " + comment.getUser().getLastName();
//                long likesCount = commentService.getLikesCount(comment.getId());
//                List<String> likers = commentLikeService.getLikersForComment(comment.getId());
//
//                return new CommentDto(
//                        comment.getId(),
//                        comment.getContent(),
//                        displayName,
//                        comment.getCreatedAt(),
//                        likesCount,
//                        likers
//                );
//            })
//            .toList();
//
//        modelAndView.addObject("post", post);
//        modelAndView.addObject("comments", commentDtos);
//        modelAndView.addObject("newComment", new Comment());
//
//        return modelAndView;
//}
}

