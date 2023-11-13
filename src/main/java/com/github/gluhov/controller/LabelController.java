package com.github.gluhov.controller;

import com.github.gluhov.model.Label;
import com.github.gluhov.model.Status;
import com.github.gluhov.repository.gson.GsonLabelRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LabelController {
    private final GsonLabelRepositoryImpl gsonLabelRepository;

    public LabelController(GsonLabelRepositoryImpl gsonPostRepositoryImpl) {
        this.gsonLabelRepository = gsonPostRepositoryImpl;
    }

    public Optional<Label> get(Long id) {
        return gsonLabelRepository.getById(id);
    }

    public void delete(Long id) {
        gsonLabelRepository.deleteById(id);
    }

    public Label create(Label l) { return gsonLabelRepository.save(l); }

    public void update(Label l) {
        gsonLabelRepository.save(l);
    }

    public List<Label> findAll() {
        return gsonLabelRepository.findAll().stream()
                .filter(l -> l.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
