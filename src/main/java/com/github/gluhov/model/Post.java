package com.github.gluhov.model;

import java.util.List;

public class Post extends BaseEntity {
    private String title;
    private String content;
    private List<Long> labelsId;

    private List<Long> writersId;

    public Post(String title, String content, List<Long> labelsId, List<Long> writersId, Status status) {
        super(status);
        this.title = title;
        this.content = content;
        this.labelsId = labelsId;
        this.writersId = writersId;
    }

    public Post(Post p) {
        this(p.title, p.content, p.labelsId, p.writersId, p.getStatus());
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

    public List<Long> getLabelsId() {
        return labelsId;
    }

    public void setLabelsId(List<Long> labelsId) {
        this.labelsId = labelsId;
    }

    public List<Long> getWritersId() {
        return writersId;
    }

    public void setWritersId(List<Long> writersId) {
        this.writersId = writersId;
    }
}
