package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Friend;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface FriendRepository extends CrudRepository<Friend, Long> {
    List<Friend> findAllByMainUserId(Long mainUserId);


    Optional<Friend> findByMainUserIdAndFriendUserId(Long currentUserId, Long friendId);
}
