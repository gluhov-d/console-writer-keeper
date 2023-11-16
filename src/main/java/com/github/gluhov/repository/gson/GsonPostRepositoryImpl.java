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

    private List<Post> readPostsFromFile() {
        return FileUtil.readFromFile(filePath, new TypeToken<List<Post>>(){}.getType());
    }

    private void writePostsToFile(List<Post> posts) { FileUtil.writeToFile(filePath, posts);}

    private Long generateIncrementedId(List<Post> posts) {
        return posts.stream().mapToLong(Post::getId).max().orElse(0) + 1;
    }

    @Override
    public Post save(Post post) {
        List<Post> posts = readPostsFromFile();
        if (post.getId() == null) {
            post.setId(generateIncrementedId(posts));
            posts.add(post);
        } else {
            posts = posts.stream()
                    .map(p -> Objects.equals(p.getId(), post.getId()) ? post : p)
                    .collect(Collectors.toList());
        }
        writePostsToFile(posts);
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
        List<Post> posts = readPostsFromFile().stream()
                        .peek((p -> {
                            if (p.getId().equals(id)) {
                                p.setStatus(Status.DELETED);
                            }
                        })).collect(Collectors.toList());
        writePostsToFile(posts);
    }

    @Override
    public Boolean checkIfExists(Long id) {
        return readPostsFromFile().stream()
                .anyMatch(p -> p.getId().equals(id));
    }

    @Override
    public List<Post> findAll() { return readPostsFromFile();}

    @Override
    public void saveAll(List<Post> posts) {
        writePostsToFile(posts);
    }
}
