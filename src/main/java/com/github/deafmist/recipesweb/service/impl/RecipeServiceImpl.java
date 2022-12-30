package com.github.deafmist.recipesweb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.deafmist.recipesweb.exception.NoSuchRecipeException;
import com.github.deafmist.recipesweb.model.Recipe;
import com.github.deafmist.recipesweb.service.FileService;
import com.github.deafmist.recipesweb.service.RecipeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static int id = 0;

    private Map<Integer, Recipe> recipes = new LinkedHashMap<>();

    private FileService fileService;

    public RecipeServiceImpl(@Qualifier("fileRecipeServiceImpl") FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipes.put(id++, recipe);
        saveToFile();
    }

    @Override
    public Recipe getRecipe(int id) {
        if (!recipes.containsKey(id)) {
            throw new NoSuchRecipeException("Такого рецепта не существует!");
        }
        return recipes.get(id);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return (LinkedList<Recipe>) recipes.values();
    }

    @Override
    public void editRecipe(int id, Recipe recipe) {
        if (!recipes.containsKey(id)) {
            throw new NoSuchRecipeException("Такого рецепта не существует!");
        }
        recipes.put(id, recipe);
        saveToFile();
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка записи в файл");
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile();
            recipes = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка чтения из файла");
        }
    }
}
