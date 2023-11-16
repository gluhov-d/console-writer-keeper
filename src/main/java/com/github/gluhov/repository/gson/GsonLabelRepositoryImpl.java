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

    private List<Label> readLabelsFromFile() {
        return FileUtil.readFromFile(filePath, new TypeToken<List<Label>>(){}.getType());
    }


    private void writeLabelsToFile(List<Label> labels) {
        FileUtil.writeToFile(filePath, labels);
    }

    private Long generateIncrementedId(List<Label> labels) {
        return labels.stream().mapToLong(Label::getId).max().orElse(0) + 1;
    }

    @Override
    public Label save(Label label) {

        List<Label> labels = readLabelsFromFile();
        if (label.getId() == null) {
            label.setId(generateIncrementedId(labels));
            labels.add(label);
        } else {
            labels = labels.stream()
                    .map(l -> Objects.equals(l.getId(), label.getId()) ? label : l)
                    .collect(Collectors.toList());
        }
        writeLabelsToFile(labels);
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
        List<Label> labels = readLabelsFromFile().stream()
                .peek(l -> {
                    if (l.getId().equals(id)) {
                        l.setStatus(Status.DELETED);
                    }
                }).collect(Collectors.toList());
        writeLabelsToFile(labels);
    }

    @Override
    public List<Label> findAll() {
        return readLabelsFromFile();
    }

    @Override
    public void saveAll(List<Label> labels) {
        writeLabelsToFile(labels);
    }


    @Override
    public Boolean checkIfExists(Long id) {
        return readLabelsFromFile().stream()
                .anyMatch(l -> l.getId().equals(id));
    }
}
