package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Friend;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface FriendRepository extends CrudRepository<Friend, Long> {
    List<Friend> findAllByMainUserId(Long mainUserId);


}
