package com.github.deafmist.recipesweb.service.impl;

import com.github.deafmist.recipesweb.exception.NoSuchIngredientException;
import com.github.deafmist.recipesweb.model.Ingredient;
import com.github.deafmist.recipesweb.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static int id = 0;

    private Map<Integer, Ingredient> ingredients = new LinkedHashMap<>();

    @Override
    public void addIngredient(Ingredient ingredient) {
        ingredients.put(id++, ingredient);
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
    }

    @Override
    public boolean deleteIngredient(int id) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            return true;
        }
        return false;
    }
}
