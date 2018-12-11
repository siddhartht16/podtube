package com.podtube.feedentities;

import com.podtube.models.Category;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.podtube.common.AppConstants.*;

public class RSSFeedItemImpl implements RSSFeedItem {

    private String title;
    private Date pubDate;
    private String link;
    private String guid;
    private String author;
    private String thumbnail;
    private String description;
    private String content;
    private String enclosure_link;
    private String enclosure_type;
    private Long enclosure_length;
    private Long enclosure_duration;
    private String enclosure_thumbnail;

    public RSSFeedItemImpl(String title,
                           String pubDate,
                           String link,
                           String guid,
                           String author,
                           String thumbnail,
                           String description,
                           String content,
                           String enclosure_link,
                           String enclosure_type,
                           String enclosure_length,
                           String enclosure_duration,
                           String enclosure_thumbnail,
                           String categories) {
        this.title = title;
        this.link = link;
        this.guid = guid;
        this.author = author;
        this.thumbnail = thumbnail;
        this.description = description;
        this.content = content;
        this.enclosure_link = enclosure_link;
        this.enclosure_type = enclosure_type;
        this.enclosure_thumbnail = enclosure_thumbnail;
        this.setEnclosure_length(enclosure_length);
        this.setEnclosure_duration(enclosure_duration);
        this.setCategories(categories);
        this.setPubDate(pubDate);
    }

    private List<String> categories;

    @Override
    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = Arrays.asList(categories.split(","));
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate){
        this.pubDate = pubDate;
    }

    public void setPubDate(String pubDate) {

        DateFormat simpleDateFormat = new SimpleDateFormat(GPODDER_DATE_FORMAT);
        try {
            this.pubDate = simpleDateFormat.parse(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getEnclosure_link() {
        return enclosure_link;
    }

    public void setEnclosure_link(String enclosure_link) {
        this.enclosure_link = enclosure_link;
    }

    @Override
    public String getEnclosure_type() {
        return enclosure_type;
    }

    public void setEnclosure_type(String enclosure_type) {
        this.enclosure_type = enclosure_type;
    }

    @Override
    public Long getEnclosure_length() {
        return enclosure_length;
    }

    public void setEnclosure_length(Long enclosure_length) {
        this.enclosure_length = enclosure_length;
    }

    public void setEnclosure_length(String enclosure_length) {
        try{
            this.enclosure_length = Long.parseLong(enclosure_length);
        }
        catch (NumberFormatException nfex){
            this.enclosure_length = 0L;
        }
    }

    @Override
    public Long getEnclosure_duration() {
        return enclosure_duration;
    }

    public void setEnclosure_duration(Long enclosure_duration) {
        this.enclosure_duration = enclosure_duration;
    }

    public void setEnclosure_duration(String enclosure_duration) {
        try {
            this.enclosure_duration = Long.parseLong(enclosure_duration);
        } catch (NumberFormatException nfex) {
            this.enclosure_duration = 0L;
        }
    }

    @Override
    public String getEnclosure_thumbnail() {
        return enclosure_thumbnail;
    }

    public void setEnclosure_thumbnail(String enclosure_thumbnail) {
        this.enclosure_thumbnail = enclosure_thumbnail;
    }
}
