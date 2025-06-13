package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByUserIdOrderByTimePostedDesc(Long receivingUserId);
    List<Post> findByOrderByTimePostedDesc();
    Iterable<Post> findAllByUserId(Long id);

}
