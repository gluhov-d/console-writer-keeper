package com.github.gluhov.view;

import com.github.gluhov.controller.PostController;
import com.github.gluhov.model.Post;
import com.github.gluhov.to.PostWithLabels;
import com.github.gluhov.util.ConsoleUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostView {
    private final BufferedReader br;
    private final PostController postController;

    public PostView(BufferedReader br, PostController postController) {
        this.br = br;
        this.postController = postController;
    }

    public void displayMenu() throws IOException {
            outer:
            while(true) {
                System.out.println("--- Post menu ---");
                System.out.println("1. View post with labels");
                System.out.println("2. Create post");
                System.out.println("3. Update post");
                System.out.println("4. Delete post");
                System.out.println("5. Find all posts");
                System.out.println("6. Main menu");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(br.readLine());
                ConsoleUtil.writeEmptyLines();
                switch (choice) {
                    case 1 -> {
                        System.out.print("Id: ");
                        Long id = Long.parseLong(br.readLine());
                        Optional<PostWithLabels> post = postController.getWithWritersAndLabels(id);
                        ConsoleUtil.writeEmptyLines();
                        System.out.println("--- Operation result ---");
                        if (post.isPresent()) {
                            System.out.println(post.get());
                        } else {
                            System.out.println("No post with such id");
                        }
                        System.out.println();
                    }
                    case 2 -> {
                        System.out.print("Post title:");
                        String title = br.readLine();
                        System.out.print("Post content:");
                        String content = br.readLine();
                        List<Long> labelsId = new ArrayList<>();
                        System.out.print("Labels ids (use spaces as delimiter):");
                        String line = br.readLine();
                        if (!line.isEmpty()) {
                            String[] ids = line.split(" ");
                            for (String labelId : ids) {
                                Long lId = Long.parseLong(labelId);
                                if (postController.checkLabel(lId)) {
                                    labelsId.add(lId);
                                } else {
                                    System.out.println("--- Operation result ---");
                                    System.out.println("No label with such id");
                                    System.out.println();
                                    break outer;
                                }

                            }
                        }
                        Post createdPost = new Post();
                        createdPost.setTitle(title);
                        createdPost.setContent(content);
                        createdPost.setLabelsId(labelsId);
                        ConsoleUtil.writeEmptyLines();
                        Post created = postController.createWithLabels(createdPost);
                        System.out.println("--- Operation result ---");
                        System.out.println("Post created: ");
                        System.out.println(created);
                        System.out.println();
                    }
                    case 3 -> {
                        System.out.print("Post id: ");
                        Long updatedPostId = Long.parseLong(br.readLine());
                        Optional<Post> updatedPost = postController.get(updatedPostId);
                        if (updatedPost.isPresent()) {
                            System.out.print("Title: ");
                            String updatedTitle = br.readLine();
                            System.out.print("Content: ");
                            String updatedContent = br.readLine();
                            List<Long> labelsIds = new ArrayList<>();
                            System.out.print("Labels ids (use spaces as delimiter):");
                            String updatedLine = br.readLine();
                            if (!updatedLine.isEmpty()) {
                                String[] updatedLabelsIds = updatedLine.split(" ");
                                for (String updatedId : updatedLabelsIds) {
                                    Long ulId = Long.parseLong(updatedId);
                                    if (postController.checkLabel(ulId)) {
                                        labelsIds.add(ulId);
                                    } else {
                                        System.out.println("--- Operation result ---");
                                        System.out.println("No label with such id");
                                        System.out.println();
                                        break outer;
                                    }
                                }
                            }
                            updatedPost.get().setTitle(updatedTitle);
                            updatedPost.get().setContent(updatedContent);
                            updatedPost.get().setLabelsId(labelsIds);
                            postController.update(updatedPost.get());
                            System.out.println("--- Operation result ---");
                            System.out.println("Post updated.");
                        } else {
                            System.out.println("--- Operation result ---");
                            System.out.println("No post with such id");
                        }
                        System.out.println();
                    }
                    case 4 -> {
                        System.out.print("Post id: ");
                        Long deleteId = Long.parseLong(br.readLine());
                        try {
                            postController.delete(deleteId);
                        } catch (Exception ex) {
                            System.out.println("--- Operation result ---");
                            System.out.println("No such writer, try again");
                            break;
                        }
                        System.out.println("--- Operation result ---");
                        System.out.println("Post deleted");
                        System.out.println();
                    }
                    case 5 -> {
                        List<Post> posts = postController.findAll();
                        System.out.println("--- Operation result ---");
                        System.out.println("Available active posts: ");
                        if (posts == null) {
                            System.out.println("No posts available");
                            break;
                        }
                        for (Post p : posts) {
                            System.out.println(p);
                        }
                        System.out.println();
                    }
                    case 6 -> {
                        System.out.println("Returning to main menu");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
    }
}
