package com.github.deafmist.recipesweb.controller;

import com.github.deafmist.recipesweb.model.Ingredient;
import com.github.deafmist.recipesweb.model.Recipe;
import com.github.deafmist.recipesweb.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipes")
@Tag(name = "Реценты", description = "CRUD-операции и другие эндпоинты для работы с рецептами")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(
            summary = "Поиск рецепта по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Элемента по заданному id не существует"
            )
    }
    )
    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable("id") int id) {
        return recipeService.getRecipe(id);
    }

    @Operation(
            summary = "Данные обо всех рецептах"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты были найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    }
    )
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @Operation(
            summary = "Добавление нового рецепта"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был добавлен"
            )
    }
    )
    @PostMapping
    public void addRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
    }

    @Operation(
            summary = "Редактирование рецепта по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был найден и изменен"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Элемента по заданному id не существует"
            )
    }
    )
    @PutMapping("/{id}")
    public void editRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        recipeService.editRecipe(id, recipe);
    }

    @Operation(
            summary = "Удаление рецепта по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был найден и удален"
            )
    }
    )
    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
    }
}
