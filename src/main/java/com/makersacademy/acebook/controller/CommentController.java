
package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.dto.CommentDto;
import com.makersacademy.acebook.dto.CommentRequest;
import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.CommentService;
import com.makersacademy.acebook.service.NotificationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Locale.filter;

@Controller
@RequestMapping("/posts/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final CommentRepository commentRepository;

    public CommentController(CommentService commentService,
                             PostRepository postRepository,
                             UserRepository userRepository,
                             NotificationService notificationService,
                             CommentRepository commentRepository) {
        this.commentService = commentService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.commentRepository = commentRepository;
    }

    // ————————————
    // Return a list of CommentDto with real names
    // ————————————
    @GetMapping("/post/{postId}")
    @ResponseBody
    public List<CommentDto> getCommentsByPost(@PathVariable Long postId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size)
    {
        Page<Comment> pg = commentRepository .findByPostIdOrderByCreatedAtAsc(postId, PageRequest.of(page, size));

//        return commentService.getCommentsForPost(postId)
          return pg.getContent()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ————————————
    // Need to change to a CommentDto instead of Comment
    // ————————————
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public CommentDto createComment(@RequestBody CommentRequest request,
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

        // map to DTO
        return toDto(comment);
    }
    // ————————————
    // Mapper method: I've had to change the mapping to pull from commentDto instead of comment
    // to show their display name as JSON needs to ignore user ID.
    // ————————————
    private CommentDto toDto(Comment c) {
        User u = c.getUser(); // force-load proxy
        String displayName = u.getFirstName() + " " + u.getLastName();

        return new CommentDto(
                c.getId(),
                c.getContent(),
                displayName,
                c.getCreatedAt(),
                0L,              // stub for likesCount
                List.of(),       // stub for likers
                u.getProfilePic(),
                u.getId()
        );
    }
    @Transactional
    @PostMapping("/{commentId}/delete")
    public RedirectView deleteComment(@PathVariable Long commentId,
                                      @RequestParam Long postId,
                                      @AuthenticationPrincipal(expression = "attributes['email']") String email) {

        if (userRepository.findUserByUsername(email).get().getId()
                .equals(commentRepository.findById(commentId).get().getUser().getId())) {
            commentService.deleteComment(commentId);
            return new RedirectView("/posts/" + postId);
        } else {
            return new RedirectView("/profile");
        }
    }


}
