package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.CommentLike;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.CommentLikeRepository;
import com.makersacademy.acebook.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CommentService(CommentRepository commentRepository,
                          CommentLikeRepository commentLikeRepository) {
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
    }

    public Comment addComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsForPost(Long postId) {
        return commentRepository.findByPost_IdOrderByCreatedAtDesc(postId);
    }

    @Transactional
    public void likeComment(Long userId, Long commentId) {
        boolean alreadyLiked = commentLikeRepository
                .findByUser_IdAndComment_Id(userId, commentId)
                .isPresent();

        if (!alreadyLiked) {
            CommentLike like = new CommentLike();
            like.setUser(new User(userId));           // assumes User(Long id) constructor exists
            like.setComment(new Comment(commentId));  // assumes Comment(Long id) constructor exists
            commentLikeRepository.save(like);
        }
    }

    @Transactional
    public void unlikeComment(Long userId, Long commentId) {
        commentLikeRepository.deleteByUser_IdAndComment_Id(userId, commentId);
    }

    public long getLikesCount(Long commentId) {
        return commentLikeRepository.countByComment_Id(commentId);
    }
}
