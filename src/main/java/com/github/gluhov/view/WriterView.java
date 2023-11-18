package com.github.gluhov.view;

import com.github.gluhov.controller.WriterController;
import com.github.gluhov.model.Writer;
import com.github.gluhov.util.ConsoleUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class WriterView {
    private final Scanner sc;
    private final WriterController writerController;

    public WriterView(Scanner sc, WriterController writerController) {
        this.sc = sc;
        this.writerController = writerController;
    }

    public void displayMenu() throws IOException {
            while(true) {
                System.out.println("--- Writer menu ---");
                System.out.println("1. View writer with posts");
                System.out.println("2. Create writer");
                System.out.println("3. Update writer");
                System.out.println("4. Delete writer");
                System.out.println("5. Find all writers");
                System.out.println("6. Main menu");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
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
        List<Writer> writers = writerController.findAll();
        System.out.println("--- Operation result ---");
        System.out.println("Available active writers: ");
        if (writers == null) {
            System.out.println("No posts available");
        } else {
            for (Writer w : writers) {
                System.out.println(w);
            }
        }
        System.out.println();
    }

    private void delete() {
        System.out.print("Writer id: ");
        Long deleteId = sc.nextLong();
        try {
            writerController.delete(deleteId);
        } catch (Exception ex) {
            System.out.println("--- Operation result ---");
            System.out.println("No such writer, try again");
        }
        System.out.println("--- Operation result ---");
        System.out.println("Writer deleted");
        System.out.println();
    }

    private void update() {
        Long updatedWriterId = ConsoleUtil.readLong(sc, "Id: ");
        Optional<Writer> updatedWriter = writerController.get(updatedWriterId);
        if (updatedWriter.isPresent()) {
            System.out.print("First name: ");
            String updatedFirstName = sc.next();
            System.out.print("Last name: ");
            String updatedLastName = sc.next();
            List<Long> updatedPosts = new ArrayList<>();
            System.out.println("Posts id (one on the line) or type '-1' to Exit:");
            while(true) {
                Long postId = ConsoleUtil.readLong(sc, "Post id: ");
                if (postId == -1) {
                    break;
                }
                if (writerController.checkIfPostExists(postId)) {
                    updatedPosts.add(postId);
                } else {
                    System.out.println("--- Operation result ---");
                    System.out.println("No post with such id");
                    System.out.println();
                }
            }
            updatedWriter.get().setFirstName(updatedFirstName);
            updatedWriter.get().setLastName(updatedLastName);
            Writer updated = writerController.saveWithPosts(updatedWriter.get(), updatedPosts);
            System.out.println("--- Operation result ---");
            System.out.println("Writer updated.");
            System.out.println(updated);
        } else {
            System.out.println("--- Operation result ---");
            System.out.println("No writer with such id");
        }
        System.out.println();
    }

    private void create() {
        System.out.print("First name:");
        String firstName = sc.next();
        System.out.print("Last name:");
        String lastName = sc.next();
        List<Long> posts = new ArrayList<>();
        System.out.println("Posts id (one on the line) or type '-1' to Exit:");
        while(true) {
            Long postId = ConsoleUtil.readLong(sc, "Post id: ");
            if (postId == -1) {
                break;
            }
            if (writerController.checkIfPostExists(postId)) {
                posts.add(postId);
            } else {
                System.out.println("--- Operation result ---");
                System.out.println("No post with such id");
                System.out.println();
            }
        }
        ConsoleUtil.writeEmptyLines();
        Writer createdWriter = Writer.builder().firstName(firstName).lastName(lastName).build();
        Writer created = writerController.saveWithPosts(createdWriter, posts);
        System.out.println("--- Operation result ---");
        System.out.println("Writer created");
        System.out.println(created);
    }

    private void view() {
        System.out.print("Id: ");
        Long id = sc.nextLong();
        Optional<Writer> writer = writerController.get(id);
        ConsoleUtil.writeEmptyLines();
        System.out.println("--- Operation result ---");
        if (writer.isPresent()) {
            System.out.println(writer.get());
        } else {
            System.out.println("No writer with such id");
        }
        System.out.println();
    }
}
