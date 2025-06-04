package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {
    List<FriendRequest> findAllByReceiverIdAndStatus(Long receiverId, String status);

}
