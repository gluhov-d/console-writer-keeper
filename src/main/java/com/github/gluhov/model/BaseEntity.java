package com.github.gluhov.model;

public abstract class BaseEntity {
    private static long idCounter = 0;
    private final long id;
    private Status status;

    public BaseEntity(Status status) {
        this.id = ++idCounter;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
