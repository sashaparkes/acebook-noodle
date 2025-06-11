package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Friend;
import com.makersacademy.acebook.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface FriendRepository extends CrudRepository<Friend, Long> {
    List<Friend> findAllByMainUserId(Long mainUserId);
    Optional<Friend> findByMainUserIdAndFriendUserId(Long currentUserId, Long friendId);
    @Query("SELECT a.friendUserId FROM Friend a WHERE a.mainUserId = :currentUserId")
    List<Long> findFriendUserIdByMainUserId(Long currentUserId);
}
