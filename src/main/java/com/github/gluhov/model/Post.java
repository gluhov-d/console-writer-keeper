package com.github.gluhov.model;

import java.util.List;

public class Post extends BaseEntity {
    private String title;
    private String content;
    private List<Long> labelsId;

    public Post(String title, String content, List<Long> labelsId, Status status) {
        super(status);
        this.title = title;
        this.content = content;
        this.labelsId = labelsId;
    }

    public Post(Post p) {
        this(p.title, p.content, p.labelsId, p.getStatus());
    }

    public Post() {

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("id: " + id + "; title: " + title + "; content: " + content + "; status: " + status + "; labels id: [ ");
        if (!labelsId.isEmpty()) {
            for (int i = 0; i < labelsId.size(); i++) {
                sb.append(labelsId.get(i));
                if (i != labelsId.size() -1) {
                    sb.append(" , ");
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
}
