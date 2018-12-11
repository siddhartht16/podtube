package com.podtube.customentities;

import com.podtube.models.*;
import com.podtube.repositories.FollowLinkRepository;
import com.podtube.repositories.SubscriptionRepository;
import com.podtube.repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserPublicProfile {

    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private boolean isFollowed;
    private List<Podcast> subscribedPodcasts;
    private List<UserPublicProfile> followers;
    private List<UserPublicProfile> followees;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public List<Podcast> getSubscribedPodcasts() {
        return subscribedPodcasts;
    }

    public void setSubscribedPodcasts(List<Podcast> subscribedPodcasts) {
        this.subscribedPodcasts = subscribedPodcasts;
    }

    public List<UserPublicProfile> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserPublicProfile> followers) {
        this.followers = followers;
    }

    public List<UserPublicProfile> getFollowees() {
        return followees;
    }

    public void setFollowees(List<UserPublicProfile> followees) {
        this.followees = followees;
    }

    //***Note: Call at last after populating basic user info otherwise it might break***
    public void populateFollowees(FollowLinkRepository followLinkRepository) {
        List<FollowLink> followeeLinks = followLinkRepository.findAllByFollowerId(id);
        followees = new ArrayList<>();
        for (FollowLink followeeLink : followeeLinks) {
            User followee = followeeLink.getFollowee();
            UserPublicProfile followeeProfile = new UserPublicProfile();
            followeeProfile.setUsername(followee.getUsername());
            followeeProfile.setFirstname(followee.getFirstname());
            followee.setLastname(followee.getLastname());
            followeeProfile.setId(followee.getId());
            followees.add(followeeProfile);
        }
    }

    public void populateFollowers(FollowLinkRepository followLinkRepository) {
        List<FollowLink> followerLinks = followLinkRepository.findAllByFolloweeId(id);
        followers = new ArrayList<>();
        for (FollowLink followerLink : followerLinks) {
            User follower = followerLink.getFollower();
            UserPublicProfile followerProfile = new UserPublicProfile();
            followerProfile.setUsername(follower.getUsername());
            followerProfile.setFirstname(follower.getFirstname());
            follower.setLastname(follower.getLastname());
            followerProfile.setId(follower.getId());
            followers.add(followerProfile);
        }
    }

    public void populateSubscriptions(SubscriptionRepository subscriptionRepository,
                                      UserRepository userRepository,
                                      int loggedInUserId) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByUser_IdOrderByCreatedOnDesc(id);
        subscribedPodcasts = new ArrayList<>();
        List<Subscription> userSubscriptions = subscriptionRepository.findAllByUser_IdOrderByCreatedOnDesc(loggedInUserId);
        Set<Podcast> loggedInUserSubscribedPodcasts = new HashSet<>();
        for (Subscription loggedInUserSubscription : userSubscriptions) {
            loggedInUserSubscribedPodcasts.add(loggedInUserSubscription.getPodcast());
        }
        for (Subscription subscription : subscriptions) {
            Podcast podcast = subscription.getPodcast();
            if (loggedInUserSubscribedPodcasts.contains(podcast))
                podcast.setSubscribed(true);
            subscribedPodcasts.add(podcast);
        }
    }
}
