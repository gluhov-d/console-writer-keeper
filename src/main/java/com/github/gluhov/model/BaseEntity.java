package com.github.gluhov.model;

import java.util.Objects;

public abstract class BaseEntity {
    private static long idCounter = 0;
    protected Long id;
    protected Status status;

    public BaseEntity(Status status) {
        this.id = ++idCounter;
        this.status = status;
    }

    public BaseEntity() {
        this.status = Status.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
