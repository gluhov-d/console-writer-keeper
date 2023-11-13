package com.github.gluhov.controller;

import com.github.gluhov.model.Status;
import com.github.gluhov.model.Writer;
import com.github.gluhov.repository.gson.GsonPostRepositoryImpl;
import com.github.gluhov.repository.gson.GsonWriterRepositoryImpl;
import com.github.gluhov.service.WriterService;
import com.github.gluhov.to.WriterWithPosts;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WriterController {
    private final WriterService writerService;
    private final GsonWriterRepositoryImpl writerRepository;

    private final GsonPostRepositoryImpl postRepository;

    public WriterController(WriterService writerService, GsonWriterRepositoryImpl writerRepository, GsonPostRepositoryImpl postRepository) {
        this.writerService = writerService;
        this.writerRepository = writerRepository;
        this.postRepository = postRepository;
    }

    public Optional<Writer> get(Long id) {
        return writerRepository.getById(id);
    }

    public Optional<WriterWithPosts> getWithPostsAndLabels(Long id) {
        return writerService.getWriterWithPosts(id);
    }

    public boolean checkPost(Long id) {
        return postRepository.checkPost(id).isPresent();
    }

    public void delete(Long id) {
        writerRepository.deleteById(id);
    }

    public void update(Writer w) {
        writerRepository.save(w);
    }

    public Writer createWithPosts(Writer w) {
        return writerRepository.save(w);
    }

    public List<Writer> findAll() {
        return writerRepository.findAll().stream()
                .filter(w -> w.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
