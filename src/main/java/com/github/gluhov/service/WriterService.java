package com.github.gluhov.service;

import com.github.gluhov.model.Post;
import com.github.gluhov.model.Status;
import com.github.gluhov.model.Writer;
import com.github.gluhov.repository.PostRepository;
import com.github.gluhov.repository.WriterRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WriterService {
    private final WriterRepository writerRepository;
    private final PostService postService;
    private final PostRepository postRepository;

    public WriterService(WriterRepository writerRepository, PostRepository postRepository, PostService postService) {
        this.writerRepository = writerRepository;
        this.postRepository = postRepository;
        this.postService = postService;
    }

    public Optional<Writer> getWriterWithPosts(Long writerId) {
        Optional<Writer> writer = writerRepository.getById(writerId);
        if (writer.isEmpty()) return Optional.empty();

        List<Post> postsWithLabels = postRepository.findAll().stream()
                .filter(p -> p.getStatus().equals(Status.ACTIVE))
                .filter(p -> p.getWritersId().contains(writerId))
                .map(p -> postService.getPostWithLabels(p.getId()).get())
                .collect(Collectors.toList());
        writer.get().setPosts(postsWithLabels);

        return writer;
    }

    public Writer saveWriterWithPosts(Writer writer, List<Long> postsId) {
        Writer newWriter = writerRepository.save(writer);
        postService.updateAllWritersId(writer.getId(), postsId);
        return newWriter;
    }
}
