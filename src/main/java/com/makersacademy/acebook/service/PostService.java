package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.PostLike;
import com.makersacademy.acebook.repository.FriendRepository;
import com.makersacademy.acebook.repository.PostLikeRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostLikeRepository postLikeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    PostLikeService postLikeService;


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

    public List<Post> findFriendsPosts(Long currentUserId) {
        // Find the id's of each friend for current user
        List<Long> friendsIds = (friendRepository.findFriendUserIdByMainUserId(currentUserId));
        Iterable<Post> currentUserPosts = postRepository.findAllByUserId(currentUserId);
        List<Post> friendsPosts = new ArrayList<>();
        currentUserPosts.forEach(friendsPosts::add);

        // find the User objects for each friend of current user
        for (Long friend : friendsIds) {
            Iterable<Post> postsOfFriend = postRepository.findAllByUserId(friend);
            postsOfFriend.forEach(friendsPosts::add);
        }
        friendsPosts.sort(Collections.reverseOrder());
        return friendsPosts;
    }
}

