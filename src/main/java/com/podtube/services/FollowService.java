package com.podtube.services;

import com.podtube.customentities.ResponseWrapper;
import com.podtube.customentities.UserPublicProfile;
import com.podtube.models.*;
import com.podtube.repositories.FollowLinkRepository;
import com.podtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
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
            User userfollowee = followLink.getFollowee();
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
    public ResponseEntity<UserPublicProfile> followUser(HttpSession httpSession,
                                                              @PathVariable("userId") int followeeId) {
        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        int id = (int) httpSession.getAttribute("id");
        Optional<User> userOpt = userRepository.findById(id);

        if(!userOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = userOpt.get();

        Optional<User> followedUserOpt = userRepository.findById(followeeId);

        if(!followedUserOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User followee = followedUserOpt.get();

        FollowLink existingFollowLink = followLinkRepository.findByFolloweeIdAndFollowerId(followeeId, id);
        if (existingFollowLink != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        FollowLink followLink = new FollowLink();
        followLink.setFollowee(followee);
        followLink.setFollower(user);
        FollowLink savedFollowLink = followLinkRepository.save(followLink);
        followee = savedFollowLink.getFollowee();

        UserPublicProfile followeeProfile = new UserPublicProfile();
        followeeProfile.setId(followee.getId());
        followeeProfile.setFirstname(followee.getFirstname());
        followeeProfile.setLastname(followee.getLastname());
        followeeProfile.setUsername(followee.getUsername());
        followeeProfile.setFollwed(true);

        return new ResponseEntity<>(followeeProfile, HttpStatus.OK);
    }//followUser..

    @PostMapping("/api/unfollow/{userId}")
    public ResponseEntity<UserPublicProfile> unfollowUser(HttpSession httpSession,
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

        // check if follow link actually exists
        FollowLink followLink = followLinkRepository.findByFolloweeIdAndFollowerId(followedUser.getId(), user.getId());
        if (followLink == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User followee = followLink.getFollowee();
        followLinkRepository.delete(followLink);

        UserPublicProfile followeeProfile = new UserPublicProfile();
        followeeProfile.setId(followee.getId());
        followeeProfile.setFirstname(followee.getFirstname());
        followeeProfile.setLastname(followee.getLastname());
        followeeProfile.setUsername(followee.getUsername());
        followeeProfile.setFollwed(true);

        return new ResponseEntity<>(followeeProfile, HttpStatus.OK);
    }//unfollowUser..
}
