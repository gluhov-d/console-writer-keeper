package com.github.gluhov.controller;

import com.github.gluhov.model.Label;
import com.github.gluhov.model.Post;
import com.github.gluhov.model.Status;
import com.github.gluhov.repository.LabelRepository;
import com.github.gluhov.repository.PostRepository;
import com.github.gluhov.service.PostService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final LabelRepository labelRepository;

    public PostController(PostService postService, PostRepository postRepository, LabelRepository labelRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.labelRepository = labelRepository;
    }

    public Optional<Post> get(Long id) {
        return postRepository.getById(id);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post saveWithLabels(Post p, List<Long> labelsId) {
        return postService.saveWithLabels(p, labelsId);
    }

    public boolean checkLabelStatus(Long id) {
        Optional<Label> label = labelRepository.getById(id);
        return label.map(value -> value.getStatus().equals(Status.ACTIVE)).orElse(false);
    }

    public boolean checkIfPostExists(Long id) { return postRepository.checkIfExists(id); }

    public List<Post> findAll() {
        return postRepository.findAll().stream()
                .filter(p -> p.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
