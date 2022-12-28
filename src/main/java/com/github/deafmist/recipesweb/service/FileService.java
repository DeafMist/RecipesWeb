package com.github.deafmist.recipesweb.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileService {
    public abstract boolean saveToFile(String json);

    protected boolean saveToFile(Path path, String json) {
        try {
            cleanDataFile(path);
            Files.writeString(path, json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка записи в файл");
        }
    }

    public abstract String readFromFile();

    protected String readFromFile(Path path) {
        try {
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка чтения из файла");
        }
    }

    private boolean cleanDataFile(Path path) {
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
