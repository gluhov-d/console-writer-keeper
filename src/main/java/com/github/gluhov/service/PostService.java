package com.github.gluhov.service;

import com.github.gluhov.model.Label;
import com.github.gluhov.model.Post;
import com.github.gluhov.repository.LabelRepository;
import com.github.gluhov.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

public class PostService {
    private final PostRepository postRepository;
    private final LabelRepository labelRepository;
    public PostService(PostRepository postRepository, LabelRepository labelRepository) {
        this.postRepository = postRepository;
        this.labelRepository = labelRepository;
    }

    public Post saveWithLabels(Post post, List<Long> labelsId) {
        List<Label> labelsToSave = new ArrayList<>();
        for (Long id: labelsId) {
            labelRepository.getById(id).ifPresent(labelsToSave::add);
        }
        post.setLabels(labelsToSave);
        return postRepository.save(post);
    }
}
