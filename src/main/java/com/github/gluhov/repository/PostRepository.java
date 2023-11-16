package com.github.gluhov.repository;

import com.github.gluhov.model.Post;

import java.util.List;

public interface PostRepository extends GenericRepository<Post, Long>{

    void saveAll(List<Post> posts);
}
