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
import com.makersacademy.acebook.service.NotificationService;
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
import java.sql.Timestamp;
import java.time.Instant;
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
    @Autowired
    NotificationService notificationService;

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
        Iterable<Post> posts = postRepository.findByUserIdOrderByTimePostedDesc(id);
        Iterable<Friend> friendships = friendRepository.findAllByMainUserId(id);

        List<User> friends = new ArrayList<>();
        for (Friend friend : friendships) {
            Long friendId = friend.getFriendUserId();
            Optional<User> friendUser = userRepository.findById(friendId);
            if (friendUser.isPresent()) {
                friends.add(friendUser.get());
            }
        }

        // Get notifications count for navbar
        Integer notificationCount = notificationService.notificationCount(signedInUser.getId());

        boolean isFriend = isFriend(signedInUser.getId(), userForProfile.getId());
        boolean incomingRequest = incomingFriendRequest(signedInUser.getId(), userForProfile.getId());
        boolean outgoingRequest = outgoingFriendRequest(signedInUser.getId(), userForProfile.getId());
        boolean pendingRequest = isFriendRequest(outgoingRequest,
                incomingRequest);


        ModelAndView profile = new ModelAndView("users/profile");
        profile.addObject("notificationCount", notificationCount);
        profile.addObject("userId", userId);
        profile.addObject("user", userForProfile);
        profile.addObject("signedInUser", signedInUser);
        profile.addObject("posts", posts);
        profile.addObject("friends", friends);
        profile.addObject("isFriend", isFriend);
        profile.addObject("pendingRequest", pendingRequest);
        profile.addObject("incomingRequest", incomingRequest);
        profile.addObject("outgoingRequest", outgoingRequest);
        return profile;
    }

    @PostMapping("/profile_friend_request/{requesterId}")
    public RedirectView respondToFriendRequest(
            @PathVariable Long requesterId,
            @RequestParam String decision,
            @AuthenticationPrincipal(expression = "attributes['email']") String email) {

        // Get User!
        Optional<User> userOptional = userRepository.findUserByUsername(email);

        User currentUser = userOptional.get();
        Long currentUserId = currentUser.getId();

        // Get Friend Request!
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository
                .findByRequesterIdAndReceiverIdAndStatus(requesterId, currentUserId, "pending");


        FriendRequest friendRequest = friendRequestOptional.get();

        if (decision.equals("accept")) {
            friendRequest.setStatus("accepted");
            Instant instant = Instant.now();
            Timestamp now = Timestamp.from(instant);
            friendRequest.setRespondedAt(now);
            friendRequestRepository.save(friendRequest);


            Friend friendship1 = new Friend();
            friendship1.setMainUserId(currentUserId);
            friendship1.setFriendUserId(requesterId);
            friendship1.setFriendsSince(now);
            friendRepository.save(friendship1);

            Friend friendship2 = new Friend();
            friendship2.setMainUserId(requesterId);
            friendship2.setFriendUserId(currentUserId);
            friendship2.setFriendsSince(now);
            friendRepository.save(friendship2);

        } else if (decision.equals("decline")) {
            friendRequest.setStatus("rejected");
            Instant instant = Instant.now();
            Timestamp now = Timestamp.from(instant);
            friendRequest.setRespondedAt(now);
            friendRequestRepository.save(friendRequest);
        }

        return new RedirectView("/profile/{requesterId}");
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

    private boolean outgoingFriendRequest(Long userA, Long userB) {

        Iterable<FriendRequest> pendingFriendRequests = friendRequestRepository.findAllByRequesterIdAndStatus(userA, "pending");

        List<Long> userARequests = new ArrayList<>();
        for (FriendRequest request : pendingFriendRequests) {
            Long userBId = request.getReceiverId();
            Optional<User> userBUser = userRepository.findById(userBId);
            if (userBUser.isPresent()) {
                userARequests.add(userBUser.get().getId());
            }
        }

        if (userARequests.contains(userB)) {
            return true;
        }
        else {
            return false;
        }

    }

    private boolean incomingFriendRequest(Long userA, Long userB) {

        Iterable<FriendRequest> incomingPendingFriendRequests = friendRequestRepository.findAllByRequesterIdAndStatus(userB, "pending");

        List<Long> userBRequests = new ArrayList<>();
        for (FriendRequest request : incomingPendingFriendRequests) {
            Long userAId = request.getReceiverId();
            Optional<User> userAUser = userRepository.findById(userAId);
            if (userAUser.isPresent()) {
                userBRequests.add(userAUser.get().getId());
            }
        }


        if (userBRequests.contains(userA)) {
            return true;
        }
        else {
            return false;
        }

    }

    private boolean isFriendRequest(Boolean sent, Boolean incoming) {

        if (sent & incoming) {
            return true;
        }
        else {
            return false;
        }
    }

}
