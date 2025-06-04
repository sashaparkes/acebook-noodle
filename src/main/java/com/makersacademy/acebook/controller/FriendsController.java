package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Friend;
import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FriendRepository;
import com.makersacademy.acebook.repository.FriendRequestRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;
import java.util.ArrayList;
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

        // Friend Requests!
        List<FriendRequest> pendingRequests = friendRequestRepository.findAllByReceiverIdAndStatus(userId, "pending");

        // Objectify!
        List<User> requesterUsers = new ArrayList<>();
        for (FriendRequest request : pendingRequests) {
            Long requesterId = request.getRequesterId();
            Optional<User> requesterUser = userRepository.findById(requesterId);
            if (requesterUser.isPresent()) {
                requesterUsers.add(requesterUser.get());
            }
        }

        modelAndView.addObject("friendUsers", friendUsers);
        modelAndView.addObject("requesterUsers", requesterUsers);

        return modelAndView;

//    @PostMapping("/friends")
//    public RedirectView create(@ModelAttribute Friend friend, @AuthenticationPrincipal(expression = "attributes['email']") String email) {
//        Optional<User> user = userRepository.findUserByUsername(email);
//        if (user.isPresent()) {
//            Long id = user.get().getId();
//        }
//        return new RedirectView("friends/friends");
//    }
    }
}
