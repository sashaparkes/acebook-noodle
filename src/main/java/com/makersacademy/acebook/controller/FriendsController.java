package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Friend;
import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FriendRepository;
import com.makersacademy.acebook.repository.FriendRequestRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class FriendsController {

    @Autowired
    FriendRequestRepository friendRequestRepository;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/friends")
    public ModelAndView friendList(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        ModelAndView modelAndView = new ModelAndView("friends/friends");

        // User!
        Optional<User> userOptional = userRepository.findUserByUsername(email);

        User currentUser = userOptional.get();
        Long userId = currentUser.getId();

        // Friends!
        List<Friend> friendsList = friendRepository.findAllByMainUserId(userId);

        // Objectify!
        List<User> friendUsers = new ArrayList<>();
        for (Friend friend : friendsList) {
            Long friendId = friend.getFriendUserId();
            Optional<User> friendUser = userRepository.findById(friendId);
            if (friendUser.isPresent()) {
                friendUsers.add(friendUser.get());
            }
        }

        // Sort the users into alphabetical order!
        friendUsers.sort(Comparator.comparing(User::getFirstName));


        // Friend Requests!
        List<FriendRequest> pendingRequests = friendRequestRepository.findAllByReceiverIdAndStatusOrderByCreatedAtDesc(userId, "pending");


        // Objectify!
        List<User> requesterUsers = new ArrayList<>();
        for (FriendRequest request : pendingRequests) {
            Long requesterId = request.getRequesterId();
            Optional<User> requesterUser = userRepository.findById(requesterId);
            if (requesterUser.isPresent()) {
                requesterUsers.add(requesterUser.get());
            }
        }


        // Get notifications count for navbar
        Integer notificationCount = notificationService.notificationCount(currentUser.getId());

        modelAndView.addObject("notificationCount", notificationCount);
        modelAndView.addObject("friendUsers", friendUsers);
        modelAndView.addObject("requesterUsers", requesterUsers);
        modelAndView.addObject("currentUser", currentUser);

        return modelAndView;
    }


    @PostMapping("/friend_request/{requesterId}")
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

            friendRequestRepository.delete(friendRequest);

        } else if (decision.equals("decline")) {
            friendRequest.setStatus("rejected");
            Instant instant = Instant.now();
            Timestamp now = Timestamp.from(instant);
            friendRequest.setRespondedAt(now);
            friendRequestRepository.save(friendRequest);
        }

        return new RedirectView("/friends");
    }

    @PostMapping("/remove_friend/{friendId}")
    public RedirectView removeFriend(
            @PathVariable Long friendId,
            @AuthenticationPrincipal(expression = "attributes['email']") String email) {

        // Get User!
        Optional<User> userOptional = userRepository.findUserByUsername(email);

        User currentUser = userOptional.get();
        Long currentUserId = currentUser.getId();

        //Find Friendships!

        Optional<Friend> friendship1 = friendRepository.findByMainUserIdAndFriendUserId(currentUserId, friendId);
        Optional<Friend> friendship2 = friendRepository.findByMainUserIdAndFriendUserId(friendId, currentUserId);

        //Remove Friendships!
        friendship1.ifPresent(friendRepository::delete);
        friendship2.ifPresent(friendRepository::delete);

        //Redirect to Friends!
        return new RedirectView("/friends");
    }

    @PostMapping("/add_friend/{userId}")
    public RedirectView addFriend(
            @PathVariable Long userId,
            @AuthenticationPrincipal(expression = "attributes['email']") String email) {

        // Get User!
        Optional<User> userOptional = userRepository.findUserByUsername(email);

        User currentUser = userOptional.get();
        Long currentUserId = currentUser.getId();

        Optional<User> requestee = userRepository.findById(userId);

        User requesteeUser = requestee.get();
        Long requesteeId = requesteeUser.getId();

        FriendRequest request = new FriendRequest();
        request.setRequesterId(currentUserId);
        request.setReceiverId(requesteeId);
        request.setStatus("pending"); // Set status
        request.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Set timestamp
        friendRequestRepository.save(request);

        return new RedirectView("/profile/{userId}");
    }

    @GetMapping("/friends/{userId}")
    public ModelAndView profileFriendList(@AuthenticationPrincipal(expression = "attributes['email']") String email,
                                          @PathVariable("userId") Long id) {
        ModelAndView modelAndView = new ModelAndView("friends/profile_friends");

        // User!
        Optional<User> userOptional = userRepository.findUserByUsername(email);
        User currentUser = userOptional.get();

        // Profile User!
        Optional<User> profileUserOptional = userRepository.findById(id);
        User profileUser = profileUserOptional.get();

        // Friends!
        List<Friend> friendsList = friendRepository.findAllByMainUserId(id);

        // Objectify!
        List<User> friendUsers = new ArrayList<>();
        for (Friend friend : friendsList) {
            Long friendId = friend.getFriendUserId();
            Optional<User> friendUser = userRepository.findById(friendId);
            if (friendUser.isPresent()) {
                friendUsers.add(friendUser.get());
            }
        }

        // Sort the users into alphabetical order!
        friendUsers.sort(Comparator.comparing(User::getFirstName));

        // Get notifications count for navbar
        Integer notificationCount = notificationService.notificationCount(currentUser.getId());

        modelAndView.addObject("notificationCount", notificationCount);
        modelAndView.addObject("friendUsers", friendUsers);
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("profileUser", profileUser);

        return modelAndView;
    }

}

