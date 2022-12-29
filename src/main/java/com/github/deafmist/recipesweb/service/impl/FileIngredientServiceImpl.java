package com.github.deafmist.recipesweb.service.impl;

import com.github.deafmist.recipesweb.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
public class FileIngredientServiceImpl extends FileService {
    @Value("${dataPath}")
    private String dataFilePath;

    @Value("${ingredientsDataFile}")
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

    @Override
    public File getDataFile() {
        return new File(dataFilePath + "/" + dataFileName);
    }
}
