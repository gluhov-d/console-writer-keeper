package com.github.gluhov.repository;

import com.github.gluhov.model.Label;

import java.util.List;

public interface LabelRepository extends GenericRepository<Label, Long> {
    void saveAll(List<Label> labels);
}
