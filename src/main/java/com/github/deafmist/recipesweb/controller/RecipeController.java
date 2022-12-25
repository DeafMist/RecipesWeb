package com.github.deafmist.recipesweb.controller;

import com.github.deafmist.recipesweb.model.Recipe;
import com.github.deafmist.recipesweb.service.RecipeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable("id") int id) {
        return recipeService.getRecipe(id);
    }

    @PostMapping
    public void addRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
    }
}
