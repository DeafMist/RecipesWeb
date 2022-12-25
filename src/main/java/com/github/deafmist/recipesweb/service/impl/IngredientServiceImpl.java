package com.github.deafmist.recipesweb.service.impl;

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
        return ingredients.get(id);
    }
}