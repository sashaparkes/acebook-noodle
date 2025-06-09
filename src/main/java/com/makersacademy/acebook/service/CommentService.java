package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.repository.CommentLikeRepository;
import com.makersacademy.acebook.repository.CommentRepository;
import org.springframework.stereotype.Service;

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
        return commentRepository.findByPost_IdOrderByCreatedAtAsc(postId);
    }

    public long getLikesCount(Long commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void deleteCommentsByPostId(Long postId) {
        commentRepository.deleteAllByPostId(postId);
    }
}

