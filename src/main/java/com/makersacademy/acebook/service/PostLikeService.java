package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.PostLike;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostLikeRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public PostLikeService(PostLikeRepository postLikeRepository, UserRepository userRepository, NotificationService notificationService) {
        this.postLikeRepository = postLikeRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public void likePost(Long postId, String userEmail) {
        User user = userRepository.findUserByUsername(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<PostLike> existingLike = postLikeRepository.findByUserIdAndPostId(user.getId(), postId);

        if (existingLike.isEmpty()) {
            PostLike like = new PostLike();
            like.setUserId(user.getId());
            like.setPostId(postId);
            postLikeRepository.save(like);
            notificationService.newNotification(user.getId(), "postlike", null, like, null);
        }

    }

    public long getLikesCount(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

    public void unlikePost(Long postId, String userEmail) {
        User user = userRepository.findUserByUsername(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        postLikeRepository.deleteByUserIdAndPostId(user.getId(), postId);
    }

    public List<String> getLikersForPost(Long postId) {
        List<PostLike> likes = postLikeRepository.findByPostId(postId);
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
