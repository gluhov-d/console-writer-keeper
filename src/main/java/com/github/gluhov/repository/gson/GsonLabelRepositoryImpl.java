package com.github.gluhov.repository.gson;

import com.github.gluhov.model.Label;
import com.github.gluhov.model.Status;
import com.github.gluhov.repository.LabelRepository;
import com.github.gluhov.util.FileUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class GsonLabelRepositoryImpl implements LabelRepository {
    private final String filePath = "labels.json";

    public GsonLabelRepositoryImpl() {
    }

    @Override
    public Label save(Label label) {
        List<Label> labels = findAll();
        if (label.getId() == null) {
            label = new Label(label);
            labels.add(label);
        } else {
            Label finalLabel = label;
            labels = labels.stream()
                    .map(l -> Objects.equals(l.getId(), finalLabel.getId()) ? finalLabel : l)
                    .collect(Collectors.toList());
        }
        FileUtil.writeToFile(filePath, labels);
        return label;
    }

    @Override
    public Optional<Label> getById(Long id) {
        return findAll().stream()
                .filter(label -> label.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        List<Label> labels = findAll();
        labels.forEach(label -> {
            if (label.getId().equals(id)) {
                label.setStatus(Status.DELETED);
            }
        });
        FileUtil.writeToFile(filePath, labels);
    }

    @Override
    public List<Label> findAll() {
        return FileUtil.readFromFile(filePath, new TypeToken<List<Label>>(){}.getType());
    }
}
