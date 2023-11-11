package com.github.gluhov.repository.gson;

import com.github.gluhov.model.Status;
import com.github.gluhov.model.Writer;
import com.github.gluhov.repository.WriterRepository;
import com.github.gluhov.util.FileUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class GsonWriterRepositoryImpl implements WriterRepository {

    private final String filePath = "writers.json";

    public GsonWriterRepositoryImpl() {}

    @Override
    public Writer save(Writer writer) {
        List<Writer> writers = findAll();
        if (writer.getId() == null) {
            writer = new Writer(writer);
            writers.add(writer);
        } else {
            Writer finalWriter = writer;
            writers = writers.stream()
                    .map(w -> Objects.equals(w.getId(), finalWriter.getId()) ? finalWriter : w)
                    .collect(Collectors.toList());
        }
        FileUtil.writeToFile(filePath, writers);
        return writer;
    }

    @Override
    public Optional<Writer> getById(Long id) {
        return findAll().stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        List<Writer> writers = findAll();
        writers.forEach(writer -> {
            if(writer.getId().equals(id)) {
                writer.setStatus(Status.DELETED);
            }
        });
        FileUtil.writeToFile(filePath, writers);
    }

    @Override
    public List<Writer> findAll() {
        return FileUtil.readFromFile(filePath, new TypeToken<List<Writer>>(){}.getType());
    }
}
