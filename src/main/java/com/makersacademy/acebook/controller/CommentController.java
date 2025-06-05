//package com.makersacademy.acebook.controller;
//
//import com.makersacademy.acebook.dto.CommentRequest;
//import com.makersacademy.acebook.model.Comment;
//import com.makersacademy.acebook.model.Post;
//import com.makersacademy.acebook.model.User;
//import com.makersacademy.acebook.repository.PostRepository;
//import com.makersacademy.acebook.repository.UserRepository;
//import com.makersacademy.acebook.service.CommentService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/post/comments")
//public class CommentController {
//
//    private final CommentService commentService;
//    private final PostRepository postRepository;
//    private final UserRepository userRepository;
//
//    public CommentController(CommentService commentService, PostRepository postRepository, UserRepository userRepository) {
//        this.commentService = commentService;
//        this.postRepository = postRepository;
//        this.userRepository = userRepository;
//    }
//
//    @GetMapping("/post/{postId}")
//    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
//        return commentService.getCommentsForPost(postId);
//    }
//
//    @PostMapping
//    public Comment createComment(@RequestBody CommentRequest request) {
//        Post post = postRepository.findById(request.getPostId())
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//        User user = userRepository.findById(request.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Long commentId = 0L;
//        Comment comment = new Comment(commentId);
//        comment.setPost(post);
//        comment.setUser(user);
//        comment.setContent(request.getContent());
//
//        return commentService.addComment(comment);
//    }
//}
//
