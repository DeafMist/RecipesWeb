package com.github.deafmist.recipesweb.service.impl;

import com.github.deafmist.recipesweb.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class FileRecipeServiceImpl extends FileService {
    @Value("${dataPath}")
    private String dataFilePath;

    @Value("${recipesDataFile}")
    private String dataFileName;

    @Override
    public boolean saveToFile(String json) {
        return this.saveToFile(Path.of(dataFilePath, dataFileName), json);
    }

    @Override
    protected boolean saveToFile(Path path, String json) {
        return super.saveToFile(path, json);
    }

    @Override
    public String readFromFile() {
        return this.readFromFile(Path.of(dataFilePath, dataFileName));
    }

    @Override
    protected String readFromFile(Path path) {
        return super.readFromFile(path);
    }
}
