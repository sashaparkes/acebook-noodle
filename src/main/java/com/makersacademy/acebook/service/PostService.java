package com.makersacademy.acebook.service;
import com.makersacademy.acebook.service.PostService;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.PostLike;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostLikeRepository;
import com.makersacademy.acebook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostLikeRepository postLikeRepository;

    public PostService(PostRepository postRepository,
                          PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
    }

    public Post newPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public void likePost(Long userId, Long postId) {
        boolean alreadyLiked = postLikeRepository
                .findByUserIdAndPostId(userId, postId)
                .isPresent();

        if (!alreadyLiked) {
            PostLike like = new PostLike();
            like.setUserId(userId);
            like.setPostId(postId);
            postLikeRepository.save(like);
        }
    }

    @Transactional
    public void unlikePost(Long userId, Long postId) {
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }

    public long getLikesCount(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }
}