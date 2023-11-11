package com.github.gluhov.model;

import java.util.List;

public class Label extends BaseEntity{
    private String name;

    private List<Long> postsId;

    public Label(Status status, String name, List<Long> postsId) {
        super(status);
        this.name = name;
        this.postsId = postsId;
    }

    public Label(Label l) {
        this(l.getStatus(), l.name, l.postsId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getPostsId() {
        return postsId;
    }

    public void setPostsId(List<Long> postsId) {
        this.postsId = postsId;
    }
}
