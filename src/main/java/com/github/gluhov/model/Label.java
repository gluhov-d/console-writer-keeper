package com.github.gluhov.model;

public class Label extends BaseEntity{
    private String name;

    public Label(Status status, String name) {
        super(status);
        this.name = name;
    }

    public Label() {
    }

    public Label(Label l) {
        this(l.getStatus(), l.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id: " + id + "; name: " + name + "; status: " + status + ";";
    }
}
