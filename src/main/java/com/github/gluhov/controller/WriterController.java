package com.github.gluhov.controller;

import com.github.gluhov.model.Post;
import com.github.gluhov.model.Status;
import com.github.gluhov.model.Writer;
import com.github.gluhov.repository.PostRepository;
import com.github.gluhov.repository.WriterRepository;
import com.github.gluhov.service.WriterService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class WriterController {
    private final WriterService writerService;
    private final WriterRepository writerRepository;
    private final PostRepository postRepository;

    public Optional<Writer> get(Long id) {
        return writerRepository.getById(id);
    }

    public boolean checkPostStatus(Long id) {
        Optional<Post> post = postRepository.getById(id);
        return post.map(value -> value.getStatus().equals(Status.ACTIVE)).orElse(false);
    }

    public boolean checkIfWriterExists(Long id) { return writerRepository.checkIfExists(id); }

    public void delete(Long id) {
        writerRepository.deleteById(id);
    }

    public Writer saveWithPosts(Writer w, List<Long> postsId) {
        return writerService.saveWithPosts(w, postsId);
    }

    public List<Writer> findAll() {
        return writerRepository.findAll().stream()
                .filter(w -> w.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
