package com.github.gluhov.service;

import com.github.gluhov.model.Label;
import com.github.gluhov.model.Post;
import com.github.gluhov.model.Status;
import com.github.gluhov.repository.gson.GsonLabelRepositoryImpl;
import com.github.gluhov.repository.gson.GsonPostRepositoryImpl;
import com.github.gluhov.to.PostWithLabels;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostService {
    private final GsonPostRepositoryImpl gsonPostRepositoryImpl;
    private final GsonLabelRepositoryImpl gsonLabelRepositoryImpl;

    public PostService(GsonPostRepositoryImpl gsonPostRepositoryImpl, GsonLabelRepositoryImpl gsonLabelRepositoryImpl) {
        this.gsonPostRepositoryImpl = gsonPostRepositoryImpl;
        this.gsonLabelRepositoryImpl = gsonLabelRepositoryImpl;
    }

    public Optional<PostWithLabels> getPostWithLabels(Long postId) {
        Optional<Post> post = gsonPostRepositoryImpl.getById(postId);
        if (post.isEmpty()) return Optional.empty();
        List<Long> labelsId = post.get().getLabelsId();
        List<Label> labels = gsonLabelRepositoryImpl.findAll().stream()
                .filter(l -> l.getStatus().equals(Status.ACTIVE))
                .filter(l -> labelsId.contains(l.getId()))
                .collect(Collectors.toList());

        return Optional.of(new PostWithLabels(post.get().getId(), post.get().getTitle(), post.get().getContent(), post.get().getStatus(), labels));
    }
}
