package com.github.gluhov.repository.gson;

import com.github.gluhov.model.Post;
import com.github.gluhov.model.Status;
import com.github.gluhov.repository.PostRepository;
import com.github.gluhov.util.FileUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class GsonPostRepositoryImpl implements PostRepository {
    private final String filePath = "posts.json";

    public GsonPostRepositoryImpl() {
    }

    @Override
    public Post save(Post post) {
        List<Post> posts = findAll();
        if (post.getId() == null) {
            post = new Post(post);
            posts.add(post);
        } else {
            Post finalPost = post;
            posts = posts.stream()
                    .map(p -> Objects.equals(p.getId(), finalPost.getId()) ? finalPost : p)
                    .collect(Collectors.toList());
        }
        FileUtil.writeToFile(filePath, posts);
        return post;
    }

    @Override
    public Optional<Post> getById(Long id) {
        return findAll().stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        List<Post> posts = findAll();
        posts.forEach(post -> {
            if (post.getId().equals(id)) {
                post.setStatus(Status.DELETED);
            }
        });
        FileUtil.writeToFile(filePath, posts);
    }

    @Override
    public List<Post> findAll() {
        return FileUtil.readFromFile(filePath, new TypeToken<List<Post>>(){}.getType());
    }
}
