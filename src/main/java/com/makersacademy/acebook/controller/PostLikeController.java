package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.service.PostLikeService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/posts")
public class PostLikeController {
    private final PostLikeService postLikeService;
    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @Transactional
    @PostMapping("/posts/{postId}/like")
    public RedirectView likePost(@PathVariable Long postId,
                                    @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        postLikeService.likePost(postId, email);
        return new RedirectView("/posts/" + postId);
    }

    @Transactional
    @PostMapping("/posts/{postId}/unlike")
    public RedirectView unlikePost(@PathVariable Long postId,
                                 @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        postLikeService.unlikePost(postId, email);
        return new RedirectView("/posts/" + postId);
    }
}