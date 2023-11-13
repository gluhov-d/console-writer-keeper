package com.github.gluhov.service;

import com.github.gluhov.model.Writer;
import com.github.gluhov.repository.gson.GsonWriterRepositoryImpl;
import com.github.gluhov.to.PostWithLabels;
import com.github.gluhov.to.WriterWithPosts;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WriterService {

    private final GsonWriterRepositoryImpl writerRepository;
    private final PostService postService;

    public WriterService(GsonWriterRepositoryImpl writerRepository, PostService postService) {
        this.writerRepository = writerRepository;
        this.postService = postService;
    }

    public Optional<WriterWithPosts> getWriterWithPosts(Long writerId) {
        Optional<Writer> writer = writerRepository.getById(writerId);
        if (writer.isEmpty()) return Optional.empty();

        List<Optional<PostWithLabels>> postsWithLabels = writer.get().getPostsId().stream()
                .map(postService::getPostWithLabels)
                .collect(Collectors.toList());

        return Optional.of(new WriterWithPosts(writer.get().getId(), writer.get().getFirstName(), writer.get().getLastName(), writer.get().getStatus(), postsWithLabels));
    }
}
