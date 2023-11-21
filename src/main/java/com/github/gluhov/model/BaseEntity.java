package com.github.gluhov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public abstract class BaseEntity {
    protected Long id;
    protected Status status;

    public BaseEntity() {
        this.status = Status.ACTIVE;
    }
}
