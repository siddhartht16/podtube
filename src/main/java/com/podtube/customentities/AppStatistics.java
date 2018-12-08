package com.podtube.customentities;

import lombok.Data;

@Data
public class AppStatistics {

    private long noOfUsers;
    private long noOfCategories;
    private long noOfPodcasts;
    private long noOfEpisodes;

    public AppStatistics() {
    }

    public long getNoOfUsers() {
        return noOfUsers;
    }

    public void setNoOfUsers(long noOfUsers) {
        this.noOfUsers = noOfUsers;
    }

    public long getNoOfCategories() {
        return noOfCategories;
    }

    public void setNoOfCategories(long noOfCategories) {
        this.noOfCategories = noOfCategories;
    }

    public long getNoOfPodcasts() {
        return noOfPodcasts;
    }

    public void setNoOfPodcasts(long noOfPodcasts) {
        this.noOfPodcasts = noOfPodcasts;
    }

    public long getNoOfEpisodes() {
        return noOfEpisodes;
    }

    public void setNoOfEpisodes(long noOfEpisodes) {
        this.noOfEpisodes = noOfEpisodes;
    }
}
