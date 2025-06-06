package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.service.CommentLikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/posts/comments")
public class CommentLikeController {
    private final CommentLikeService commentLikeService;
    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping("/{commentId}/like")
    public RedirectView likeComment(@PathVariable Long commentId,
                                    @RequestParam Long postId,
                                    @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        commentLikeService.likeComment(commentId, email);
        return new RedirectView("/posts/" + postId);
    }
}
