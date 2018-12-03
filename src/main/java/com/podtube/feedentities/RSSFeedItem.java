package com.podtube.feedentities;

import com.podtube.models.Category;

import java.util.Date;
import java.util.List;

public interface RSSFeedItem {
    public String getTitle();
    public Date getPubDate();
    public String getLink();
    public String getGuid();
    public String getAuthor();
    public String getThumbnail();
    public String getDescription();
    public String getContent();
    public String getEnclosure_link();
    public String getEnclosure_type();
    public Long getEnclosure_length();
    public Long getEnclosure_duration();
    public String getEnclosure_thumbnail();
    public List<String> getCategories();
}
