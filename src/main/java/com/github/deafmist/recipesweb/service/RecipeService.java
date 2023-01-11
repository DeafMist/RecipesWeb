package com.github.deafmist.recipesweb.service;

import com.github.deafmist.recipesweb.model.Recipe;

import java.util.List;
import java.util.Map;

public interface RecipeService {
    void addRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    List<Recipe> getAllRecipes();

    void editRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);

    byte[] exportTxt();
}
