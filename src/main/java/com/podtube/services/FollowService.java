package com.podtube.services;

import com.podtube.customentities.ResponseWrapper;
import com.podtube.customentities.UserPublicProfile;
import com.podtube.models.*;
import com.podtube.repositories.FollowLinkRepository;
import com.podtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FollowService {

    @Autowired
    FollowLinkRepository followLinkRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/followers")
    public ResponseEntity<List<UserPublicProfile>> getAllFollowers(HttpSession httpSession) {

        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        int id = (int) httpSession.getAttribute("id");
        Optional<User> userOpt = userRepository.findById(id);

        if(!userOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = userOpt.get();

        //Get user followers
        List<FollowLink> followersLinkList = followLinkRepository.findAllByFolloweeId(user.getId());

        List<UserPublicProfile> followersList = new ArrayList<>();

        //Populate followers list
        for(FollowLink followLink: followersLinkList){
            User follower = followLink.getFollower();
            UserPublicProfile userPublicProfile = new UserPublicProfile();
            userPublicProfile.setId(follower.getId());
            userPublicProfile.setFirstname(follower.getFirstname());
            userPublicProfile.setLastname(follower.getLastname());
            userPublicProfile.setUsername(follower.getUsername());
            followersList.add(userPublicProfile);
        }

        return new ResponseEntity<>(followersList, HttpStatus.OK);
    }//getAllFollowers..

    @GetMapping("/api/followees")
    public ResponseEntity<List<UserPublicProfile>> getAllFollowees(HttpSession httpSession) {

        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        int id = (int) httpSession.getAttribute("id");
        Optional<User> userOpt = userRepository.findById(id);

        if(!userOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = userOpt.get();

        List<FollowLink> userFolloweesLinkList = followLinkRepository.findAllByFollowerId(user.getId());
        List<UserPublicProfile> followeesList = new ArrayList<>();

        //Populate followees list
        for(FollowLink followLink: userFolloweesLinkList){
            User userfollowee = followLink.getFollower();
            UserPublicProfile userPublicProfile = new UserPublicProfile();
            userPublicProfile.setId(userfollowee.getId());
            userPublicProfile.setFirstname(userfollowee.getFirstname());
            userPublicProfile.setLastname(userfollowee.getLastname());
            userPublicProfile.setUsername(userfollowee.getUsername());
            followeesList.add(userPublicProfile);
        }

        return new ResponseEntity<>(followeesList, HttpStatus.OK);
    }//getAllFollowees..

    @PostMapping("/api/follow/{userId}")
    public ResponseEntity<ResponseWrapper> followUser(HttpSession httpSession,
                                                              @PathVariable("userId") int userId) {
        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        int id = (int) httpSession.getAttribute("id");
        Optional<User> userOpt = userRepository.findById(id);

        if(!userOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = userOpt.get();

        Optional<User> followedUserOpt = userRepository.findById(userId);

        if(!followedUserOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User followedUser = followedUserOpt.get();

        FollowLink followLink = new FollowLink();
        followLink.setFollowee(followedUser);
        followLink.setFollower(user);

        ResponseWrapper responseWrapper = new ResponseWrapper();

        try {
            FollowLink savedFollowLink = followLinkRepository.save(followLink);
            responseWrapper.setSuccess(true);
        }
        catch (Exception e){
            responseWrapper.setSuccess(false);
        }

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }//followUser..

    @PostMapping("/api/unfollow/{userId}")
    public ResponseEntity<ResponseWrapper> unfollowUser(HttpSession httpSession,
                                                        @PathVariable("userId") int userId) {

        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        int id = (int) httpSession.getAttribute("id");
        Optional<User> userOpt = userRepository.findById(id);

        if(!userOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = userOpt.get();

        Optional<User> unfollowedUserOpt = userRepository.findById(userId);

        if(!unfollowedUserOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User followedUser = unfollowedUserOpt.get();

        FollowLink followLink = followLinkRepository.findByFolloweeIdAndFollowerId(followedUser.getId(), user.getId());

        ResponseWrapper responseWrapper = new ResponseWrapper();

        try {
            followLinkRepository.delete(followLink);
            responseWrapper.setSuccess(true);
        }
        catch (Exception e){
            responseWrapper.setSuccess(false);
        }

        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }//unfollowUser..
}
