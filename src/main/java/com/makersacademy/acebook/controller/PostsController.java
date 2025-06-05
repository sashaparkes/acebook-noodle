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

    @GetMapping("/posts")
    public String index(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());

        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String username = (String) principal.getAttributes().get("email");
        User user = userRepository.findUserByUsername(username).get();
        model.addAttribute("user", user);

        return "posts/index";
    }

    @PostMapping("/posts")
    public RedirectView create(@ModelAttribute Post post, @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Optional<User> user = userRepository.findUserByUsername(email);
        if (user.isPresent()) {
            post.setUser_id(user.get().getId());
            postRepository.save(post);
        }
        return new RedirectView("/posts");
    }

    @GetMapping("/posts/{id}")
    public ModelAndView viewPost(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("posts/post");
        ModelAndView errorView = new ModelAndView("genericErrorPage");

        Optional<Post> currentPost = postRepository.findById(id);
        if (currentPost.isEmpty()) {
            return errorView;
        }

        Post post = currentPost.get();
        List<Comment> commentEntities = commentService.getCommentsForPost(id);

        List<CommentDto> commentDtos = commentEntities.stream()
                .map(comment -> {
                    String displayName = comment.getUser().getFirstName() + " " + comment.getUser().getLastName();
                    long likesCount = commentService.getLikesCount(comment.getId());
                    List<String> likers = commentLikeService.getLikersForComment(comment.getId());

                    return new CommentDto(
                            comment.getId(),
                            comment.getContent(),
                            displayName,
                            comment.getCreatedAt(),
                            likesCount,
                            likers
                    );
                })
                .toList();

        modelAndView.addObject("post", post);
        modelAndView.addObject("comments", commentDtos);
        modelAndView.addObject("newComment", new Comment());

        return modelAndView;
    }

}

