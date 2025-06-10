package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.dto.CommentRequest;
import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.CommentService;
import com.makersacademy.acebook.service.NotificationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import jakarta.transaction.Transactional;


@Controller
@RequestMapping("/posts/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final CommentRepository commentRepository;

    public CommentController(CommentService commentService, PostRepository postRepository, UserRepository userRepository,NotificationRepository notificationRepository, NotificationService notificationService, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.commentRepository = commentRepository;
    }


    // Show comments for specific post
    @GetMapping("/post/{postId}")
    @ResponseBody
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsForPost(postId);
    }

    // This will POST to add a comment and then redirect back to the post with the updated comment for that post
    @PostMapping
    public RedirectView createComment(@ModelAttribute CommentRequest request,
                                      @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findUserByUsername(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(request.getContent());

        commentService.addComment(comment);
        notificationService.newNotification(user.getId(), "comment", comment, null, null);
        return new RedirectView("/posts/" + post.getId());
    }


    @Transactional
    @PostMapping("/{commentId}/delete")
    public RedirectView deleteComment(@PathVariable Long commentId,
                                      @RequestParam Long postId,
                                      @AuthenticationPrincipal(expression = "attributes['email']") String email) {

        if (userRepository.findUserByUsername(email).get().getId() == commentRepository.findById(commentId).get().getUser().getId()) {
            commentService.deleteComment(commentId);
            return new RedirectView("/posts/" + postId);
        } else {
            return new RedirectView("/profile");
        }
    }
}

