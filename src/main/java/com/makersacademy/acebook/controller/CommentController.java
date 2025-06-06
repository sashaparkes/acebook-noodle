package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.dto.CommentRequest;
import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.CommentService;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/posts/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentController(CommentService commentService, PostRepository postRepository, UserRepository userRepository) {
        this.commentService = commentService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


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
        return new RedirectView("/posts/" + post.getId());
    }


}
