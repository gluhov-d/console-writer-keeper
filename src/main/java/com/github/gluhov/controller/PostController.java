package com.github.gluhov.controller;

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

    public Optional<Post> getWithLabels(Long id) {
        return postService.getPostWithLabels(id);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public void update(Post p) {
        postRepository.save(p);
    }

    public boolean checkIfExists(Long id) {
        return labelRepository.checkIfExists(id);
    }

    public Post createWithLabels(Post p, List<Long> labelsId) {
        return postService.savePostWithLabels(p, labelsId);
    }

    public List<Post> findAll() {
        return postRepository.findAll().stream()
                .filter(w -> w.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
