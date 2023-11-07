package com.github.gluhov.model;

import java.util.List;

public class Post extends BaseEntity {
    private String title;
    private String content;
    private List<Label> labels;

    public Post(String title, String content, List<Label> labels, Status status) {
        super(status);
        this.title = title;
        this.content = content;
        this.labels = labels;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}
