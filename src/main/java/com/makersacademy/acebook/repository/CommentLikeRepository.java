package com.makersacademy.acebook.repository;
import com.makersacademy.acebook.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserIdAndCommentId(Long userId, Long commentId);
    List<CommentLike> findByCommentId(Long commentId);
    long countByCommentId(Long commentId);
    void deleteByUserIdAndCommentId(Long userId, Long commentId);
}
