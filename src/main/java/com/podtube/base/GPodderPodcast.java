package com.podtube.base;

public interface GPodderPodcast {

    String getUrl();
    String getTitle();
    String getDescription();
    int getSubscribers();
    int getSubscribersLastWeek();
    String getLogoUrl();
    String getScaledLogoUrl();
    String getWebsite();
    String getMyGPOLink();
}
