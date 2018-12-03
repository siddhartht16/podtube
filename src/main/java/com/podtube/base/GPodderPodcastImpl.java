package com.podtube.base;

public class GPodderPodcastImpl implements GPodderPodcast{

    private String url;
    private String title;
    private String description;
    private int subscribers;
    private int subscribersLastWeek;
    private String logoUrl;
    private String scaledLogoUrl;
    private String website;
    private String myGPOLink;

    public GPodderPodcastImpl(String url,
                              String title,
                              String description,
                              int subscribers,
                              int subscribersLastWeek,
                              String logoUrl,
                              String scaledLogoUrl,
                              String website,
                              String myGPOLink) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.subscribers = subscribers;
        this.subscribersLastWeek = subscribersLastWeek;
        this.logoUrl = logoUrl;
        this.scaledLogoUrl = scaledLogoUrl;
        this.website = website;
        this.myGPOLink = myGPOLink;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public void setSubscribersLastWeek(int subscribersLastWeek) {
        this.subscribersLastWeek = subscribersLastWeek;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public void setScaledLogoUrl(String scaledLogoUrl) {
        this.scaledLogoUrl = scaledLogoUrl;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setMyGPOLink(String myGPOLink) {
        this.myGPOLink = myGPOLink;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getSubscribers() {
        return subscribers;
    }

    @Override
    public int getSubscribersLastWeek() {
        return subscribersLastWeek;
    }

    @Override
    public String getLogoUrl() {
        return logoUrl;
    }

    @Override
    public String getScaledLogoUrl() {
        return scaledLogoUrl;
    }

    @Override
    public String getWebsite() {
        return website;
    }

    @Override
    public String getMyGPOLink() {
        return myGPOLink;
    }
}
