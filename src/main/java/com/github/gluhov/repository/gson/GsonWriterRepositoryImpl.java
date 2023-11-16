package com.github.gluhov.repository.gson;

import com.github.gluhov.model.Status;
import com.github.gluhov.model.Writer;
import com.github.gluhov.util.FileUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class GsonWriterRepositoryImpl implements com.github.gluhov.repository.WriterRepository {

    private final String filePath = "writers.json";

    private List<Writer> readWritersFromFile() {
        return FileUtil.readFromFile(filePath, new TypeToken<List<Writer>>(){}.getType());
    }


    private void writeWritersToFile(List<Writer> writers) {
        FileUtil.writeToFile(filePath, writers);
    }

    private Long generateIncrementedId(List<Writer> writers) {
        return writers.stream().mapToLong(Writer::getId).max().orElse(0) + 1;
    }

    @Override
    public Writer save(Writer writer) {
        List<Writer> writers = readWritersFromFile();

        if (writer.getId() == null) {
            writer.setId(generateIncrementedId(writers));
            writers.add(writer);
        } else {
            writers = writers.stream()
                    .map(w -> Objects.equals(w.getId(), writer.getId()) ? writer : w)
                    .collect(Collectors.toList());
        }
        writeWritersToFile(writers);
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
        List<Writer> writers = readWritersFromFile().stream()
                        .peek(w -> {
                            if (w.getId().equals(id)) {
                                w.setStatus(Status.DELETED);
                            }
                        }).collect(Collectors.toList());
        writeWritersToFile(writers);
    }

    @Override
    public List<Writer> findAll() { return readWritersFromFile();}

    @Override
    public Boolean checkIfExists(Long id) {
        return readWritersFromFile().stream()
                .anyMatch(w -> w.getId().equals(id));
    }
}
