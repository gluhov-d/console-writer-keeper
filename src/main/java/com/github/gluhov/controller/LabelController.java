package com.github.gluhov.controller;

import com.github.gluhov.model.Label;
import com.github.gluhov.model.Status;
import com.github.gluhov.repository.LabelRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class LabelController {
    private final LabelRepository labelRepository;

    public Optional<Label> get(Long id) {
        return labelRepository.getById(id);
    }

    public void delete(Long id) {
        labelRepository.deleteById(id);
    }

    public Label save(Label l) { return labelRepository.save(l); }

    public List<Label> findAll() {
        return labelRepository.findAll().stream()
                .filter(l -> l.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
