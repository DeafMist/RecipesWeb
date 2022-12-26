package com.github.deafmist.recipesweb.service;

import com.github.deafmist.recipesweb.model.Ingredient;

public interface IngredientService {
    void addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int id);
}
