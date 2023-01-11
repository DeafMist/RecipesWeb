package com.github.deafmist.recipesweb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.deafmist.recipesweb.exception.NoSuchIngredientException;
import com.github.deafmist.recipesweb.model.Ingredient;
import com.github.deafmist.recipesweb.model.Recipe;
import com.github.deafmist.recipesweb.service.FileService;
import com.github.deafmist.recipesweb.service.IngredientService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static int id = 0;

    private Map<Integer, Ingredient> ingredients = new LinkedHashMap<>();

    private FileService fileService;

    public IngredientServiceImpl(@Qualifier("fileIngredientServiceImpl") FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        ingredients.put(id++, ingredient);
        saveToFile();
    }

    @Override
    public Ingredient getIngredient(int id) {
        if (!ingredients.containsKey(id)) {
            throw new NoSuchIngredientException("Такого ингредиента не существует!");
        }
        return ingredients.get(id);
    }

    @Override
    public Map<Integer, Ingredient> getAllIngredients() {
        return ingredients;
    }

    @Override
    public void editIngredient(int id, Ingredient ingredient) {
        if (!ingredients.containsKey(id)) {
            throw new NoSuchIngredientException("Такого ингредиента не существует!");
        }
        ingredients.put(id, ingredient);
        saveToFile();
    }

    @Override
    public boolean deleteIngredient(int id) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    private void saveToFile() {
        try {
            DataFile dataFile = new DataFile(id + 1, ingredients);
            String json = new ObjectMapper().writeValueAsString(ingredients);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка записи в файл");
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile();
            DataFile dataFile = new ObjectMapper().readValue(json, new TypeReference<DataFile>() {
            });
            id = dataFile.getId();
            ingredients = dataFile.getIngredients();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка чтения из файла");
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class DataFile {
        private int id;

        private Map<Integer, Ingredient> ingredients;
    }
}
