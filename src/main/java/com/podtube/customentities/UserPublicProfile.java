package com.podtube.customentities;

public class UserPublicProfile {

    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private boolean isFollwed;

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

    public boolean isFollwed() {
        return isFollwed;
    }

    public void setFollwed(boolean follwed) {
        isFollwed = follwed;
    }
}
