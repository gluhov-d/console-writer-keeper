package com.github.gluhov.util;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtil {
    private static final Gson gson = new Gson();
    private static final String resourceDirectory = "src/main/resources";

    public static <T> void writeToFile(String filePath, List<T> data) {
        try (FileWriter file = new FileWriter(new File(resourceDirectory, filePath))) {
            gson.toJson(data, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> readFromFile(String filePath, Type typeOf) {
        File file = new File(resourceDirectory, filePath);
        if (!file.exists()) {
            try{
                file.createNewFile();
                writeToFile(filePath, new ArrayList<>());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, typeOf);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
