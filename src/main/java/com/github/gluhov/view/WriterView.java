package com.github.gluhov.view;

import com.github.gluhov.controller.WriterController;
import com.github.gluhov.model.Writer;
import com.github.gluhov.util.ConsoleUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WriterView {
    private final BufferedReader br;
    private final WriterController writerController;

    public WriterView(BufferedReader br, WriterController writerController) {
        this.br = br;
        this.writerController = writerController;
    }

    public void displayMenu() throws IOException {
            outer:
            while(true) {
                System.out.println("--- Writer menu ---");
                System.out.println("1. View writer with posts");
                System.out.println("2. Create writer");
                System.out.println("3. Update writer");
                System.out.println("4. Delete writer");
                System.out.println("5. Find all writers");
                System.out.println("6. Main menu");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(br.readLine());
                ConsoleUtil.writeEmptyLines();
                switch (choice) {
                    case 1 -> {
                        System.out.print("Id: ");
                        Long id = Long.parseLong(br.readLine());
                        Optional<Writer> writer = writerController.getWithPostsAndLabels(id);
                        ConsoleUtil.writeEmptyLines();
                        System.out.println("--- Operation result ---");
                        if (writer.isPresent()) {
                            System.out.println(writer.get());
                        } else {
                            System.out.println("No writer with such id");
                        }
                        System.out.println();
                    }
                    case 2 -> {
                        System.out.print("First name:");
                        String firstName = br.readLine();
                        System.out.print("Last name:");
                        String lastName = br.readLine();
                        List<Long> posts = new ArrayList<>();
                        System.out.print("Posts ids (use spaces as delimiter):");
                        String line = br.readLine();
                        if (!line.isEmpty()) {
                            String[] postIds = line.split(" ");
                            for (String postId : postIds) {
                                Long pId = Long.parseLong(postId);
                                if (writerController.checkIfExists(pId)) {
                                    posts.add(pId);
                                } else {
                                    System.out.println("--- Operation result ---");
                                    System.out.println("No post with such id");
                                    System.out.println();
                                    break outer;
                                }
                            }
                        }
                        ConsoleUtil.writeEmptyLines();
                        Writer createdWriter = new Writer();
                        createdWriter.setFirstName(firstName);
                        createdWriter.setLastName(lastName);
                        Writer created = writerController.createWithPosts(createdWriter, posts);
                        System.out.println("--- Operation result ---");
                        System.out.println("Writer created");
                        System.out.println(created);
                    }
                    case 3 -> {
                        System.out.print("Writer id: ");
                        Long updatedWriterId = Long.parseLong(br.readLine());
                        Optional<Writer> updatedWriter = writerController.get(updatedWriterId);
                        if (updatedWriter.isPresent()) {
                            System.out.print("First name: ");
                            String updatedFirstName = br.readLine();
                            System.out.print("Last name: ");
                            String updatedLastName = br.readLine();
                            List<Long> updatedPosts = new ArrayList<>();
                            System.out.print("Writers ids (use spaces as delimiter):");
                            String updatedLine = br.readLine();
                            if (!updatedLine.isEmpty()) {
                                String[] updatedPostsIds = updatedLine.split(" ");
                                for (String updatedId : updatedPostsIds) {
                                    Long upId = Long.parseLong(updatedId);
                                    if (writerController.checkIfExists(upId)) {
                                        updatedPosts.add(upId);
                                    } else {
                                        System.out.println("--- Operation result ---");
                                        System.out.println("No post with such id");
                                        System.out.println();
                                        break outer;
                                    }
                                }
                            }
                            updatedWriter.get().setFirstName(updatedFirstName);
                            updatedWriter.get().setLastName(updatedLastName);
                            writerController.updateWithPosts(updatedWriter.get(),updatedPosts);
                            System.out.println("--- Operation result ---");
                            System.out.println("Writer updated.");
                        } else {
                            System.out.println("--- Operation result ---");
                            System.out.println("No writer with such id");
                        }
                        System.out.println();
                    }
                    case 4 -> {
                        System.out.print("Writer id: ");
                        Long deleteId = Long.parseLong(br.readLine());
                        try {
                            writerController.delete(deleteId);
                        } catch (Exception ex) {
                            System.out.println("--- Operation result ---");
                            System.out.println("No such writer, try again");
                            break;
                        }
                        System.out.println("--- Operation result ---");
                        System.out.println("Writer deleted");
                        System.out.println();
                    }
                    case 5 -> {
                        List<Writer> writers = writerController.findAll();
                        System.out.println("--- Operation result ---");
                        System.out.println("Available active writers: ");
                        if (writers == null) {
                            System.out.println("No posts available");
                            break;
                        }
                        for (Writer w : writers) {
                            System.out.println(w);
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
