package com.github.deafmist.recipesweb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.deafmist.recipesweb.exception.NoSuchRecipeException;
import com.github.deafmist.recipesweb.model.Ingredient;
import com.github.deafmist.recipesweb.model.Recipe;
import com.github.deafmist.recipesweb.service.FileService;
import com.github.deafmist.recipesweb.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static int id = 0;

    private Map<Integer, Recipe> recipes = new LinkedHashMap<>();

    private FileService fileService;

    @Value("${dataPath}")
    private String templatePath;

    @Value("${recipesTemplate}")
    private String templateName;

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
        return (List<Recipe>) recipes.values();
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

    @Override
    public byte[] exportTxt() {
        try {
            Path pathToTxtTemplate = Path.of(templatePath, templateName);
            String template = Files.readString(pathToTxtTemplate, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            for (Recipe recipe : recipes.values()) {
                StringBuilder ingredientsSB = new StringBuilder();
                StringBuilder stepsSB = new StringBuilder();
                for (Ingredient ingredient : recipe.getIngredients()) {
                    ingredientsSB.append(" - ").append(ingredient).append("\n");
                }
                int stepCounter = 1;
                for (String step : recipe.getSteps()) {
                    stepsSB.append(stepCounter++).append(". ").append(step).append("\n");
                }
                String recipeData = template.replace("%name%", recipe.getName())
                        .replace("%cookingTime%", String.valueOf(recipe.getCookingTime()))
                        .replace("%ingredients%", ingredientsSB.toString())
                        .replace("%steps%", stepsSB.toString());
                stringBuilder.append(recipeData).append("\n\n\n");
            }
            return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveToFile() {
        try {
            DataFile dataFile = new DataFile(id + 1, recipes);
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
            DataFile dataFile = new ObjectMapper().readValue(json, new TypeReference<DataFile>() {
            });
            id = dataFile.getId();
            recipes = dataFile.getRecipes();
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

        private Map<Integer, Recipe> recipes;
    }
}
