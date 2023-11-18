package com.github.gluhov.service;

import com.github.gluhov.model.Post;
import com.github.gluhov.model.Writer;
import com.github.gluhov.repository.PostRepository;
import com.github.gluhov.repository.WriterRepository;

import java.util.ArrayList;
import java.util.List;

public class WriterService {
    private final WriterRepository writerRepository;
    private final PostRepository postRepository;

    public WriterService(WriterRepository writerRepository, PostRepository postRepository) {
        this.writerRepository = writerRepository;
        this.postRepository = postRepository;
    }

    public Writer saveWithPosts(Writer writer, List<Long> postsId) {
        List<Post> postsToSave = new ArrayList<>();
        for (Long id: postsId) {
            postRepository.getById(id).ifPresent(postsToSave::add);
        }
        writer.setPosts(postsToSave);
        return writerRepository.save(writer);
    }
}
