package com.github.gluhov.view;

import com.github.gluhov.controller.LabelController;
import com.github.gluhov.controller.PostController;
import com.github.gluhov.controller.WriterController;
import com.github.gluhov.repository.LabelRepository;
import com.github.gluhov.repository.PostRepository;
import com.github.gluhov.repository.WriterRepository;
import com.github.gluhov.repository.gson.GsonLabelRepositoryImpl;
import com.github.gluhov.repository.gson.GsonPostRepositoryImpl;
import com.github.gluhov.repository.gson.GsonWriterRepositoryImpl;
import com.github.gluhov.service.LabelService;
import com.github.gluhov.service.PostService;
import com.github.gluhov.service.WriterService;
import com.github.gluhov.util.ConsoleUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainView {
    private final WriterController writerController;
    private final PostController postController;
    private final LabelController labelController;
    public MainView() {
        WriterRepository writerRepository = new GsonWriterRepositoryImpl();
        PostRepository postRepository = new GsonPostRepositoryImpl();
        LabelRepository labelRepository = new GsonLabelRepositoryImpl();
        LabelService labelService = new LabelService(labelRepository);
        PostService postService = new PostService(postRepository, labelRepository, labelService);
        WriterService writerService = new WriterService(writerRepository, postRepository, postService);
        this.writerController = new WriterController(writerService, writerRepository, postRepository);
        this.postController = new PostController(postService, postRepository, labelRepository);
        this.labelController = new LabelController(labelRepository);
    }

    public void displayMenu() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            WriterView writerView = new WriterView(br, writerController);
            PostView postView = new PostView(br, postController);
            LabelView labelView = new LabelView(br, labelController);
            ConsoleUtil.writeEmptyLines();
            while (true) {
                System.out.println("--- Writer keeper menu ---");
                System.out.println("1. Writers menu");
                System.out.println("2. Posts menu");
                System.out.println("3. Labels menu");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(br.readLine());
                ConsoleUtil.writeEmptyLines();
                switch (choice) {
                    case 1 -> writerView.displayMenu();
                    case 2 -> postView.displayMenu();
                    case 3 -> labelView.displayMenu();
                    case 6 -> {
                        System.out.println("Exiting application...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException ex) {
            System.out.println("IO Exception. Try again.");
        }
    }
}
