package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.dto.CommentDto;
import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.FriendRepository;
import com.makersacademy.acebook.repository.FriendRequestRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.CommentLikeService;
import com.makersacademy.acebook.service.CommentService;
import com.makersacademy.acebook.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.*;

@Controller
public class ProfileController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    FriendRequestRepository friendRequestRepository;

    @GetMapping("/profile/{userId}")
    public ModelAndView profile(@PathVariable("userId") Long id) {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String username = (String) principal.getAttributes().get("email");
        User signedInUser = userRepository.findUserByUsername(username).get();
        Long userId = (Long) principal.getAttributes().get("id");
        User userForProfile = userRepository.findById(id).get();
        Iterable<Post> posts = postRepository.findAllByUserId(id);
        Iterable<Friend> friendships = friendRepository.findAllByMainUserId(id);

        List<User> friends = new ArrayList<>();
        for (Friend friend : friendships) {
            Long friendId = friend.getFriendUserId();
            Optional<User> friendUser = userRepository.findById(friendId);
            if (friendUser.isPresent()) {
                friends.add(friendUser.get());
            }
        }

        boolean isFriend = isFriend(signedInUser.getId(), userForProfile.getId());
        boolean pendingRequest = isFriendRequest(signedInUser.getId(), userForProfile.getId());

        ModelAndView profile = new ModelAndView("users/profile");
        profile.addObject("userId", userId);
        profile.addObject("user", userForProfile);
        profile.addObject("signedInUser", signedInUser);
        profile.addObject("posts", posts);
        profile.addObject("friends", friends);
        profile.addObject("isFriend", isFriend);
        profile.addObject("pendingRequest", pendingRequest);
        return profile;
    }

    private boolean isFriend(Long userId, Long friendId) {

        Iterable<Friend> signedInFriendships = friendRepository.findAllByMainUserId(userId);

        List<Long> friends = new ArrayList<>();
        for (Friend friend : signedInFriendships) {
            Long friendUserId = friend.getFriendUserId();
            Optional<User> signedInFriendUser = userRepository.findById(friendUserId);
            if (signedInFriendUser.isPresent()) {
                friends.add(signedInFriendUser.get().getId());
            }
        }

        if (friends.contains(friendId)) {
            return true;
        }
        else {
            return false;
        }

    }

    private boolean isFriendRequest(Long requesterId, Long receiverId) {

        Iterable<FriendRequest> pendingFriendRequests = friendRequestRepository.findAllByRequesterIdAndStatus(requesterId, "pending");

        List<Long> requests = new ArrayList<>();
        for (FriendRequest request : pendingFriendRequests) {
            Long receiverUserId = request.getReceiverId();
            Optional<User> receiverUser = userRepository.findById(receiverUserId);
            if (receiverUser.isPresent()) {
                requests.add(receiverUser.get().getId());
            }
        }

        Iterable<FriendRequest> incomingPendingFriendRequests = friendRequestRepository.findAllByReceiverIdAndStatus(receiverId, "pending");

        List<Long> incomingRequests = new ArrayList<>();
        for (FriendRequest request : incomingPendingFriendRequests) {
            Long requesterUserId = request.getRequesterId();
            Optional<User> requesterUser = userRepository.findById(requesterUserId);
            if (requesterUser.isPresent()) {
                incomingRequests.add(requesterUser.get().getId());
            }
        }


        if (requests.contains(receiverId) || incomingRequests.contains(receiverId)) {
            return true;
        }
        else {
            return false;
        }

    }

}
