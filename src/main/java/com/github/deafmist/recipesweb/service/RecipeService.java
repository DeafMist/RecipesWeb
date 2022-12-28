package com.github.deafmist.recipesweb.service;

import com.github.deafmist.recipesweb.model.Recipe;

import java.util.Map;

public interface RecipeService {
    void addRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    Map<Integer, Recipe> getAllRecipes();

    void editRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);
}
