package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.service.CommentLikeService;
import jakarta.transaction.Transactional;
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

    @Transactional
    @PostMapping("/{commentId}/like")
    public RedirectView likeComment(@PathVariable Long commentId,
                                    @RequestParam Long postId,
                                    @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        commentLikeService.likeComment(commentId, email);
        return new RedirectView("/posts/" + postId);
    }

    @Transactional
    @PostMapping("/{commentId}/unlike")
    public RedirectView unlikeComment(@PathVariable Long commentId,
                                    @RequestParam Long postId,
                                    @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        commentLikeService.unlikeComment(commentId, email);
        return new RedirectView("/posts/" + postId);
    }
}