package com.github.gluhov.repository;

import com.github.gluhov.model.Post;

import java.util.Optional;

public interface PostRepository extends GenericRepository<Post, Long>{
    default Optional<Post> checkPost(Long id) {
        return getById(id);
    }
}
