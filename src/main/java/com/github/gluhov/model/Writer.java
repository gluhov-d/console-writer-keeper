package com.github.gluhov.model;

import java.util.List;

public class Writer extends BaseEntity{
    private String firstName;
    private String lastName;
    private List<Long> postsId;

    public Writer(String firstName, String lastName, List<Long> postsId, Status status) {
        super(status);
        this.firstName = firstName;
        this.lastName = lastName;
        this.postsId = postsId;
    }

    public Writer() {
    }

    public Writer(Writer w) {
        this(w.firstName, w.lastName, w.postsId, w.getStatus());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Long> getPostsId() {
        return postsId;
    }

    public void setPostsId(List<Long> postsId) {
        this.postsId = postsId;
    }
}
