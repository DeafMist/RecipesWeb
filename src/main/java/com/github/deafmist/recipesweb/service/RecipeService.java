package com.github.deafmist.recipesweb.service;

import com.github.deafmist.recipesweb.model.Recipe;

public interface RecipeService {
    void addRecipe(Recipe recipe);

    Recipe getRecipe(int id);
}
