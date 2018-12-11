package com.podtube.gpodderentities;

public class GPodderCategoryImpl implements GPodderCategory {

    private String title;
    private String tag;
    private int tagUsage;

    public GPodderCategoryImpl(String title, String tag, int usage) {
        this.title = title;
        this.tag = tag;
        this.tagUsage = usage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public int getTagUsage() {
        return tagUsage;
    }

    public void setTagUsage(int tagUsage) {
        this.tagUsage = tagUsage;
    }
}
