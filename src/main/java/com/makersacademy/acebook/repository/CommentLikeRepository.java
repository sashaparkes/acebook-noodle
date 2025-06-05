package com.makersacademy.acebook.repository;
import com.makersacademy.acebook.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUser_IdAndComment_Id(Long userId, Long commentId);
    long countByComment_Id(Long commentId);
    void deleteByUser_IdAndComment_Id(Long userId, Long commentId);
}
