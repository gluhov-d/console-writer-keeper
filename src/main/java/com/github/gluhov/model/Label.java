package com.github.gluhov.model;

public class Label extends BaseEntity{
    private String name;

    public Label(Status status, String name) {
        super(status);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
