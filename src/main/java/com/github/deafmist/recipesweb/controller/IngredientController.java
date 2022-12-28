package com.github.deafmist.recipesweb.controller;

import com.github.deafmist.recipesweb.model.Ingredient;
import com.github.deafmist.recipesweb.service.IngredientService;
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

import java.util.Map;

@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ингредиенты", description = "CRUD-операции и другие эндпоинты для работы с ингредиентами")
public class IngredientController {
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(
            summary = "Поиск ингредиента по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
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
    public Ingredient getIngredient(@PathVariable("id") int id) {
        return ingredientService.getIngredient(id);
    }

    @Operation(
            summary = "Данные обо всех ингредиентах"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты были найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    }
    )
    @GetMapping
    public ResponseEntity<Map<Integer, Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @Operation(
            summary = "Добавление нового ингредиента"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был добавлен"
            )
    }
    )
    @PostMapping
    public void addIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
    }

    @Operation(
            summary = "Редактирование ингредиента по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был найден и изменен"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Элемента по заданному id не существует"
            )
    }
    )
    @PutMapping("/{id}")
    public void editIngredient(@PathVariable("id") int id, @RequestBody Ingredient ingredient) {
        ingredientService.editIngredient(id ,ingredient);
    }

    @Operation(
            summary = "Удаление ингредиента по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был найден и удален"
            )
    }
    )
    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable("id") int id) {
        ingredientService.deleteIngredient(id);
    }
}
