package com.github.gluhov.service;

import com.github.gluhov.model.Label;
import com.github.gluhov.repository.LabelRepository;

import java.util.ArrayList;
import java.util.List;

public class LabelService {
    private final LabelRepository labelRepository;
    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public void updateAllPostsId(Long postId, List<Long> labels) {
        List<Label> jsonLabels = labelRepository.findAll();
        if (labels.isEmpty()) {
            jsonLabels.stream()
                    .filter(jsonLabel -> jsonLabel.getPostsId().contains(postId))
                    .forEach(jsonLabel -> {
                        List<Long> ids = jsonLabel.getPostsId();
                        ids.removeIf(id -> id.equals(postId));
                        jsonLabel.setPostsId(ids);
                    });
        } else{
            labels.forEach(l ->
                    jsonLabels.stream()
                            .filter(jLabel -> jLabel.getId().equals(l))
                            .forEach(jLabel -> {
                                List<Long> jPostsId = jLabel.getPostsId();
                                if (jPostsId != null ) {
                                    if (!jPostsId.contains(postId)) {
                                        jPostsId.add(postId);
                                    }
                                } else {
                                    jPostsId = new ArrayList<>();
                                }
                                jLabel.setPostsId(jPostsId);
                            })
            );
        }

        labelRepository.saveAll(jsonLabels);
    }
}
