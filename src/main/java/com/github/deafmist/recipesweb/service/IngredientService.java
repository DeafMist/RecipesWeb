package com.github.deafmist.recipesweb.service;

import com.github.deafmist.recipesweb.model.Ingredient;

import java.util.Map;

public interface IngredientService {
    void addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int id);

    Map<Integer, Ingredient> getAllIngredients();

    void editIngredient(int id, Ingredient ingredient);

    boolean deleteIngredient(int id);
}
