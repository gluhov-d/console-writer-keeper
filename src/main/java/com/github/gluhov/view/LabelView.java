package com.github.gluhov.view;

import com.github.gluhov.controller.LabelController;
import com.github.gluhov.model.Label;
import com.github.gluhov.util.ConsoleUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LabelView {
    private final BufferedReader br;
    private final LabelController labelController;
    public LabelView(BufferedReader br, LabelController labelController) {
        this.br = br;
        this.labelController = labelController;
    }

    public void displayMenu() throws IOException {
        try {
            while (true) {
                System.out.println("--- Label menu ---");
                System.out.println("1. View label");
                System.out.println("2. Create label");
                System.out.println("3. Update label");
                System.out.println("4. Delete label");
                System.out.println("5. Find all labels");
                System.out.println("6. Return to main menu");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(br.readLine());
                ConsoleUtil.writeEmptyLines();
                switch (choice) {
                    case 1 -> {
                        System.out.print("Id: ");
                        Long id = Long.parseLong(br.readLine());
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
                    case 2 -> {
                        System.out.print("Label name:");
                        String labelName = br.readLine();
                        ConsoleUtil.writeEmptyLines();
                        Label newLabel = new Label();
                        newLabel.setName(labelName);
                        System.out.println("--- Operation result ---");
                        System.out.println("Label created");
                        System.out.println(labelController.create(newLabel));
                        System.out.println();
                    }
                    case 3 -> {
                        System.out.print("Id: ");
                        Long updatedId = Long.parseLong(br.readLine());
                        Optional<Label> updatedLabel = labelController.get(updatedId);
                        if (updatedLabel.isPresent()) {
                            System.out.print("Label name: ");
                            String updatedName = br.readLine();
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
                    case 4 -> {
                        System.out.print("Label id: ");
                        Long deleteId = Long.parseLong(br.readLine());
                        ConsoleUtil.writeEmptyLines();
                        labelController.delete(deleteId);
                        System.out.println("--- Operation result ---");
                        System.out.println("label deleted");
                        System.out.println();
                    }
                    case 5 -> {
                        List<Label> labels = labelController.findAll();
                        System.out.println("--- Operation result ---");
                        System.out.println("Available active labels: ");
                        if (labels == null) break;
                        for (Label l : labels) {
                            System.out.println(l);
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
        } catch (NumberFormatException ex) {
            System.out.println("--- Operation result ---");
            System.out.println("Number format exception. Try Again.");
            System.out.println();
        }
    }
}
