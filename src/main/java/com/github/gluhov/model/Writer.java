package com.github.gluhov.model;

import java.util.List;

public class Writer extends BaseEntity{
    private String firstName;
    private String lastName;
    private List<Post> posts;

    public Writer(String firstName, String lastName, List<Post> posts, Status status) {
        super(status);
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
