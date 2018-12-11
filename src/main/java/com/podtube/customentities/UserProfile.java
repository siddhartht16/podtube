package com.podtube.customentities;

import com.podtube.common.RatingType;
import com.podtube.models.Comment;
import com.podtube.models.Rating;
import com.podtube.models.User;

import java.util.Date;
import java.util.List;

public class UserProfile {

    private class UserInfo{

        private String username;
        private String password;
        private String firstname;
        private String lastname;
        private String email;
    }

    private User user;
    private List<UserPublicProfile> followers;
    private List<UserPublicProfile> following;
    private List<Comment> comments;
    private List<Rating> ratings;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserPublicProfile> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserPublicProfile> followers) {
        this.followers = followers;
    }

    public List<UserPublicProfile> getFollowing() {
        return following;
    }

    public void setFollowing(List<UserPublicProfile> following) {
        this.following = following;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
