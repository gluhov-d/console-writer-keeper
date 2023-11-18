package com.github.gluhov.view;

import com.github.gluhov.controller.PostController;
import com.github.gluhov.model.Post;
import com.github.gluhov.util.ConsoleUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PostView {
    private final Scanner sc;
    private final PostController postController;

    public PostView(Scanner sc, PostController postController) {
        this.sc = sc;
        this.postController = postController;
    }

    public void displayMenu() throws IOException {
            while(true) {
                System.out.println("--- Post menu ---");
                System.out.println("1. View post with labels");
                System.out.println("2. Create post");
                System.out.println("3. Update post");
                System.out.println("4. Delete post");
                System.out.println("5. Find all posts");
                System.out.println("6. Main menu");
                int choice = ConsoleUtil.readInt(sc, "Choose an option: ");
                ConsoleUtil.writeEmptyLines();
                switch (choice) {
                    case 1 -> view();
                    case 2 -> create();
                    case 3 -> update();
                    case 4 -> delete();
                    case 5 -> findAll();
                    case 6 -> {
                        System.out.println("Returning to main menu");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
    }

    private void findAll() {
        List<Post> posts = postController.findAll();
        System.out.println("--- Operation result ---");
        System.out.println("Available active posts: ");
        if (posts == null) {
            System.out.println("No posts available");
        } else {
            for (Post p : posts) {
                System.out.println(p);
            }
        }
        System.out.println();
    }

    private void view() {
        Long id = ConsoleUtil.readLong(sc, "Id: ");
        Optional<Post> post = postController.get(id);
        ConsoleUtil.writeEmptyLines();
        System.out.println("--- Operation result ---");
        if (post.isPresent()) {
            System.out.println(post.get());
        } else {
            System.out.println("No post with such id");
        }
        System.out.println();
    }

    private void create() {
        System.out.print("Post title:");
        String title = sc.next();
        System.out.print("Post content:");
        String content = sc.next();
        List<Long> labels = new ArrayList<>();
        System.out.println("Labels id (one on the line) or type '-1' to Exit:");
        while(true) {
            Long labelId = ConsoleUtil.readLong(sc, "Label id: ");
            if (labelId == -1) {
                break;
            }
            if (postController.checkIfLabelExists(labelId)) {
                labels.add(labelId);
            } else {
                System.out.println("--- Operation result ---");
                System.out.println("No label with such id");
                System.out.println();
            }
        }
        Post createdPost = new Post();
        createdPost.setTitle(title);
        createdPost.setContent(content);
        ConsoleUtil.writeEmptyLines();
        Post created = postController.saveWithLabels(createdPost, labels);
        System.out.println("--- Operation result ---");
        System.out.println("Post created: ");
        System.out.println(created);
        System.out.println();
    }

    private void update() {
        Long updatedPostId = ConsoleUtil.readLong(sc, "Id: ");
        Optional<Post> updatedPost = postController.get(updatedPostId);
        if (updatedPost.isPresent()) {
            System.out.print("Title: ");
            String updatedTitle = sc.next();
            System.out.print("Content: ");
            String updatedContent = sc.next();
            List<Long> updatedLabels = new ArrayList<>();
            System.out.println("Labels id (one on the line) or type '-1' to Exit:");
            while(true) {
                Long labelId = ConsoleUtil.readLong(sc, "Label id: ");
                if (labelId == -1) {
                    break;
                }
                if (postController.checkIfLabelExists(labelId)) {
                    updatedLabels.add(labelId);
                } else {
                    System.out.println("--- Operation result ---");
                    System.out.println("No label with such id");
                    System.out.println();
                }
            }
            updatedPost.get().setTitle(updatedTitle);
            updatedPost.get().setContent(updatedContent);
            Post updated = postController.saveWithLabels(updatedPost.get(), updatedLabels);
            System.out.println("--- Operation result ---");
            System.out.println(updated);
        } else {
            System.out.println("--- Operation result ---");
            System.out.println("No post with such id");
        }
        System.out.println();
    }

    private void delete(){
        Long deleteId = ConsoleUtil.readLong(sc, "Id: ");
        try {
            postController.delete(deleteId);
        } catch (Exception ex) {
            System.out.println("--- Operation result ---");
            System.out.println("No such writer, try again");
        }
        System.out.println("--- Operation result ---");
        System.out.println("Post deleted");
        System.out.println();
    }
}
