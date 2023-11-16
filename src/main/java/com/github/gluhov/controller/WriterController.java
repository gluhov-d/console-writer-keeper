package com.github.gluhov.controller;

import com.github.gluhov.model.Status;
import com.github.gluhov.model.Writer;
import com.github.gluhov.repository.PostRepository;
import com.github.gluhov.repository.WriterRepository;
import com.github.gluhov.service.WriterService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WriterController {
    private final WriterService writerService;
    private final WriterRepository writerRepository;
    private final PostRepository postRepository;

    public WriterController(WriterService writerService, WriterRepository writerRepository, PostRepository postRepository) {
        this.writerService = writerService;
        this.writerRepository = writerRepository;
        this.postRepository = postRepository;
    }

    public Optional<Writer> get(Long id) {
        return writerRepository.getById(id);
    }

    public Optional<Writer> getWithPostsAndLabels(Long id) {
        return writerService.getWriterWithPosts(id);
    }

    public boolean checkIfExists(Long id) {
        return postRepository.checkIfExists(id);
    }

    public void delete(Long id) {
        writerRepository.deleteById(id);
    }

    public void updateWithPosts(Writer w, List<Long> writersId) {
        writerService.saveWriterWithPosts(w, writersId);
    }

    public Writer createWithPosts(Writer w, List<Long> postsId) {
        return writerService.saveWriterWithPosts(w, postsId);
    }

    public List<Writer> findAll() {
        return writerRepository.findAll().stream()
                .filter(w -> w.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
