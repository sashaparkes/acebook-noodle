package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.PostLike;
import com.makersacademy.acebook.repository.PostLikeRepository;
import com.makersacademy.acebook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public void unlikePost(Long userId, Long postId) {
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }

    public long getLikesCount(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}

