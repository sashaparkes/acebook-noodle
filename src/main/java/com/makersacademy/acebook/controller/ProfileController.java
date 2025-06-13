package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.FriendRepository;
import com.makersacademy.acebook.repository.FriendRequestRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Controller
public class ProfileController {

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

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

        // Get signed-in user from Auth0
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

        // Build friends list
        List<User> friends = new ArrayList<>();
        for (Friend friend : friendships) {
            Long friendId = friend.getFriendUserId();
            Optional<User> friendUser = userRepository.findById(friendId);
            friendUser.ifPresent(friends::add);
        }

        // Build likeCounts and commentCounts maps
        Map<Long, Long> likeCounts = new HashMap<>();
        Map<Long, Long> commentCounts = new HashMap<>();
        for (Post post : posts) {
            likeCounts.put(post.getId(), postService.getLikesCount(post.getId()));
            commentCounts.put(post.getId(), (long) commentService.getCommentsForPost(post.getId()).size());
        }

        // Get notifications count for navbar
        Integer notificationCount = notificationService.notificationCount(signedInUser.getId());

        boolean isFriend = isFriend(signedInUser.getId(), userForProfile.getId());
        boolean incomingRequest = incomingFriendRequest(signedInUser.getId(), userForProfile.getId());
        boolean outgoingRequest = outgoingFriendRequest(signedInUser.getId(), userForProfile.getId());
        boolean pendingRequest = isFriendRequest(outgoingRequest, incomingRequest);

        // Build model
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
        profile.addObject("likeCounts", likeCounts);
        profile.addObject("commentCounts", commentCounts);
        return profile;
    }

    @PostMapping("/profile_friend_request/{requesterId}")
    public RedirectView respondToFriendRequest(
            @PathVariable Long requesterId,
            @RequestParam String decision,
            @AuthenticationPrincipal(expression = "attributes['email']") String email) {

        Optional<User> userOptional = userRepository.findUserByUsername(email);
        User currentUser = userOptional.get();
        Long currentUserId = currentUser.getId();

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
        friendRequestRepository.delete(friendRequest);

        return new RedirectView("/profile/{requesterId}");
    }

    private boolean isFriend(Long userId, Long friendId) {
        Iterable<Friend> signedInFriendships = friendRepository.findAllByMainUserId(userId);
        for (Friend friend : signedInFriendships) {
            if (friend.getFriendUserId().equals(friendId)) {
                return true;
            }
        }
        return false;
    }

    private boolean outgoingFriendRequest(Long userA, Long userB) {
        Iterable<FriendRequest> pendingFriendRequests = friendRequestRepository.findAllByRequesterIdAndStatus(userA, "pending");
        for (FriendRequest request : pendingFriendRequests) {
            if (request.getReceiverId().equals(userB)) {
                return true;
            }
        }
        return false;
    }

    private boolean incomingFriendRequest(Long userA, Long userB) {
        Iterable<FriendRequest> incomingPendingFriendRequests = friendRequestRepository.findAllByRequesterIdAndStatus(userB, "pending");
        for (FriendRequest request : incomingPendingFriendRequests) {
            if (request.getReceiverId().equals(userA)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFriendRequest(Boolean sent, Boolean incoming) {
        return sent && incoming;
    }
}
