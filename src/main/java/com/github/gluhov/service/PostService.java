package com.github.gluhov.service;

import com.github.gluhov.model.Label;
import com.github.gluhov.model.Post;
import com.github.gluhov.model.Status;
import com.github.gluhov.repository.LabelRepository;
import com.github.gluhov.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostService {
    private final PostRepository postRepository;
    private final LabelRepository labelRepository;
    private final LabelService labelService;

    public PostService(PostRepository postRepository, LabelRepository labelRepository, LabelService labelService) {
        this.postRepository = postRepository;
        this.labelRepository = labelRepository;
        this.labelService = labelService;
    }

    public Post save(Post post, List<Long> labelsId) {
        labelService.updateAllPostsId(post.getId(), labelsId);
        return postRepository.save(post);
    }

    public Optional<Post> getPostWithLabels(Long postId) {
        Optional<Post> post = postRepository.getById(postId);
        if (post.isEmpty()) return Optional.empty();

        List<Label> labels = labelRepository.findAll().stream()
                .filter(l -> l.getStatus().equals(Status.ACTIVE))
                .filter(l -> l.getPostsId().contains(postId))
                .collect(Collectors.toList());

        post.get().setLabels(labels);
        return post;
    }

    public void updateAllWritersId(Long writerId, List<Long> postsId) {
        List<Post> jsonPosts = postRepository.findAll();
        if (postsId.isEmpty()) {
            jsonPosts.stream()
                    .filter(jsonPost -> jsonPost.getWritersId().contains(writerId))
                    .forEach(jsonPost -> {
                        List<Long> ids = jsonPost.getWritersId();
                        ids.removeIf(id -> id.equals(writerId));
                        jsonPost.setWritersId(ids);
                    });
        } else {
            postsId.forEach(p ->
                    jsonPosts.stream()
                            .filter(jPost -> jPost.getId().equals(p))
                            .forEach(jPost -> {
                                List<Long> jWritersId = jPost.getWritersId();
                                if (jWritersId != null) {
                                    if(!jWritersId.contains(writerId)) {
                                        jWritersId.add(writerId);
                                    } else {
                                        jWritersId = new ArrayList<>();
                                    }
                                    jPost.setWritersId(jWritersId);
                                }
                            }));
        }

        postRepository.saveAll(jsonPosts);
    }

    public Post savePostWithLabels(Post post, List<Long> labelsId) {
        if (post.getWritersId() == null) post.setWritersId(new ArrayList<>());
        Post newPost = postRepository.save(post);
        labelService.updateAllPostsId(post.getId(), labelsId);
        return newPost;
    }
}
