package com.github.deafmist.recipesweb.service.impl;

import com.github.deafmist.recipesweb.exception.NoSuchRecipeException;
import com.github.deafmist.recipesweb.model.Recipe;
import com.github.deafmist.recipesweb.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static int id = 0;

    private Map<Integer, Recipe> recipes = new LinkedHashMap<>();

    @Override
    public void addRecipe(Recipe recipe) {
        recipes.put(id++, recipe);
    }

    @Override
    public Recipe getRecipe(int id) {
        if (!recipes.containsKey(id)) {
            throw new NoSuchRecipeException("Такого рецепта не существует!");
        }
        return recipes.get(id);
    }
}
