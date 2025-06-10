package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.dto.CommentDto;
import com.makersacademy.acebook.dto.PostDto;
import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.PostLikeRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.*;
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

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.*;


@Controller
public class PostsController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostLikeRepository postLikeRepository;
    @Autowired
    PostService postService;
    @Autowired
    PostLikeService postLikeService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageStorageService imageStorageService;
    @Autowired
    NotificationService notificationService;

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


    @Value("${file.upload-dir.post-images}")
    private String uploadDir;

    // View all posts
    @GetMapping("/posts")
    public String index(Model model) {
        // Finds all posts and shows them in reverse chronological order
        Iterable<Post> posts = postRepository.findByOrderByTimePostedDesc();
        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());

        // Creates principal variable of type Default0idcUser - Spring Security class which represents a user authenticated by Auth0
        // The get commands work together to extract user attributes from Auth0 (email, given_name, family_name)
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // Uses the principal variable above to extract email and assign to username var
        // Then utilizes the 'username' variable to search the database and return matching User object
        // Adds the user object to the model (page)
        String username = (String) principal.getAttributes().get("email");
        User user = userRepository.findUserByUsername(username).get();
        Integer notificationCount = notificationService.notificationCount(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("notificationCount", notificationCount);

        return "posts/index";
    }


    // Create new post
    @PostMapping("/posts")
    public RedirectView create(
            @RequestParam("content") String content,
            @AuthenticationPrincipal(expression = "attributes['email']") String email,
            @RequestParam("image") MultipartFile file
    ) throws IOException {
        User user = userRepository.findUserByUsername(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = new Post(
                null, content, user.getId(), null, null, user);
        post = postRepository.save(post);

        String fileName = imageStorageService.storePostImage(file, String.valueOf(post.getId()));
        if (fileName != null) {
            post.setImage(fileName);
            postRepository.save(post);
        }

        return new RedirectView("/posts");
    }


    //View specific post
    @GetMapping("/posts/{id}")
    public ModelAndView viewPost(@PathVariable("id") Long id, @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        ModelAndView modelAndView = new ModelAndView("posts/post");
        ModelAndView errorView = new ModelAndView("genericErrorPage");
        Optional<Post> currentPost = postRepository.findById(id);
        if (currentPost.isEmpty()) {
            return errorView;
        }
        else {
            Post post = currentPost.get();
            String posterName = post.getUser().getFirstName() + " " + post.getUser().getLastName();
            long postLikesCount = postService.getLikesCount(post.getId());
            List<String> likedBy = postLikeService.getLikersForPost(post.getId());
            PostDto postDto = new PostDto(
                    post.getId(),
                    post.getContent(),
                    posterName,
                    post.getTimePosted(),
                    post.getImage(),
                    postLikesCount,
                    likedBy,
                    post.getUser().getProfilePic(),
                    post.getUser().getId()
            );
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
                                likers,
                                comment.getUser().getProfilePic(),
                                comment.getUser().getId()
                        );
                    })
                    .toList();
            User user = (userRepository.findUserByUsername(email)).orElse(null);
            String userId = Long.toString(user.getId());
            String userDisplayName = user.getFirstName()  + " " + user.getLastName();

            modelAndView.addObject("userId", userId);
            modelAndView.addObject("userDisplayName", userDisplayName);
            modelAndView.addObject("post", postDto);
            modelAndView.addObject("comments", commentDtos);
            modelAndView.addObject("newComment", new Comment());
            return modelAndView;
        }
    }


    @Transactional
    @PostMapping("/posts/{postId}/delete")
    public RedirectView deletePost(@PathVariable Long postId,
                                   @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        if (Objects.equals(userRepository.findUserByUsername(email).get().getId(), postRepository.findById(postId).get().getUserId())) {
            commentService.deleteCommentsByPostId(postId);
            postService.deletePost(postId);
        }
        return new RedirectView("/posts");
    }

}

