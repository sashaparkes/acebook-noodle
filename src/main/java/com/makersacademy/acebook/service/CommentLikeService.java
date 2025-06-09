package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.CommentLike;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.CommentLikeRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public CommentLikeService(CommentLikeRepository commentLikeRepository, UserRepository userRepository, NotificationService notificationService) {
        this.commentLikeRepository = commentLikeRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public void likeComment(Long commentId, String userEmail) {
        User user = userRepository.findUserByUsername(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<CommentLike> existingLike = commentLikeRepository.findByUserIdAndCommentId(user.getId(), commentId);

        if (existingLike.isEmpty()) {
            CommentLike like = new CommentLike();
            like.setUserId(user.getId());
            like.setCommentId(commentId);
            commentLikeRepository.save(like);
            notificationService.newNotification(user.getId(), "commentLike", null, null, like);
        }

    }

    public long getLikesCount(Long commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }

    public void unlikeComment(Long commentId, String userEmail) {
        User user = userRepository.findUserByUsername(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        commentLikeRepository.deleteByUserIdAndCommentId(user.getId(), commentId);
    }
    public List<String> getLikersForComment(Long commentId) {
        List<CommentLike> likes = commentLikeRepository.findByCommentId(commentId);
        return likes.stream()
                .map(like -> userRepository.findById(like.getUserId()))
                .filter(Optional::isPresent)
                .map(user -> {
                    User u = user.get();
                    return u.getFirstName() + " " + u.getLastName();
                })
                .toList();
    }

}
