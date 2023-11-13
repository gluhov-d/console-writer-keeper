package com.github.gluhov.controller;

import com.github.gluhov.model.Post;
import com.github.gluhov.model.Status;
import com.github.gluhov.repository.gson.GsonLabelRepositoryImpl;
import com.github.gluhov.repository.gson.GsonPostRepositoryImpl;
import com.github.gluhov.service.PostService;
import com.github.gluhov.to.PostWithLabels;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostController {
    private final PostService postService;
    private final GsonPostRepositoryImpl postRepository;
    private final GsonLabelRepositoryImpl labelRepository;

    public PostController(PostService postService, GsonPostRepositoryImpl gsonPostRepository, GsonLabelRepositoryImpl gsonLabelRepository) {
        this.postService = postService;
        this.postRepository = gsonPostRepository;
        this.labelRepository = gsonLabelRepository;
    }

    public Optional<Post> get(Long id) {
        return postRepository.getById(id);
    }

    public Optional<PostWithLabels> getWithWritersAndLabels(Long id) {
        return postService.getPostWithLabels(id);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public void update(Post p) {
        postRepository.save(p);
    }

    public boolean checkLabel(Long id) {
        return labelRepository.checkLabel(id).isPresent();
    }

    public Post createWithLabels(Post p) {
        return postRepository.save(p);
    }

    public List<Post> findAll() {
        return postRepository.findAll().stream()
                .filter(w -> w.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
