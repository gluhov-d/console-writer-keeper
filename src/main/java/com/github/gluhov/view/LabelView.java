package com.github.gluhov.view;

import com.github.gluhov.controller.LabelController;
import com.github.gluhov.model.Label;
import com.github.gluhov.util.ConsoleUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LabelView {
    private final Scanner sc;
    private final LabelController labelController;
    public LabelView(Scanner sc, LabelController labelController) {
        this.sc = sc;
        this.labelController = labelController;
    }

    public void displayMenu() throws IOException {
            while (true) {
                System.out.println("--- Label menu ---");
                System.out.println("1. View label");
                System.out.println("2. Create label");
                System.out.println("3. Update label");
                System.out.println("4. Delete label");
                System.out.println("5. Find all labels");
                System.out.println("6. Return to main menu");
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

    private void view() {
        Long id = ConsoleUtil.readLong(sc, "Id: ");
        Optional<Label> label = labelController.get(id);
        ConsoleUtil.writeEmptyLines();
        System.out.println("--- Operation result ---");
        if (label.isPresent()) {
            System.out.println(label.get());
        } else {
            System.out.println("No label with such id");
        }
        System.out.println();
    }

    private void create() {
        System.out.print("Label name:");
        String labelName = sc.next();
        ConsoleUtil.writeEmptyLines();
        Label newLabel = Label.builder().name(labelName).build();
        System.out.println("--- Operation result ---");
        System.out.println("Label created");
        System.out.println(labelController.save(newLabel));
        System.out.println();
    }

    private void update() {
        Long updatedId = ConsoleUtil.readLong(sc, "Id: ");
        Optional<Label> updatedLabel = labelController.get(updatedId);
        if (updatedLabel.isPresent()) {
            System.out.print("Label name: ");
            String updatedName = sc.next();
            ConsoleUtil.writeEmptyLines();
            updatedLabel.get().setName(updatedName);
            labelController.update(updatedLabel.get());
            System.out.println("--- Operation result ---");
            System.out.println("Label updated");
            System.out.println();
        } else {
            System.out.println("--- Operation result ---");
            System.out.println("No label with such id");

        }
        System.out.println();
    }

    private void delete() {
        Long deleteId = ConsoleUtil.readLong(sc, "Label id: ");
        ConsoleUtil.writeEmptyLines();
        labelController.delete(deleteId);
        System.out.println("--- Operation result ---");
        System.out.println("label deleted");
        System.out.println();
    }

    private void findAll() {
        List<Label> labels = labelController.findAll();
        System.out.println("--- Operation result ---");
        System.out.println("Available active labels: ");
        for (Label l : labels) {
            System.out.println(l);
        }
        System.out.println();
    }
}
