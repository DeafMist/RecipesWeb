package com.github.deafmist.recipesweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Тестовый контроллер")
public class FirstController {
    @Operation(
            summary = "Проверка работы приложения"
    )
    @GetMapping
    public String mainPage() {
        return "Приложение запущено";
    }

    @Operation(
            summary = "Информация о разработчике и о данном приложении"
    )
    @GetMapping("/info")
    public String infoPage() {
        return """
                Здравствуйте!<br>
                Меня зовут Проценко Роман.<br>
                Название проекта: ReceiptsWeb<br>
                Дата создания проекта: 17.12.2022<br>
                Это самая базовая после создания приложения версия для сайта рецептов. В нем, как видно из названия, будут содержаться различные рецепты и информация по приготовлению соответствующих блюд.<br>
                Проект будет реализован при помощи фреймворка Spring на языке Java версии 17.<br>
                Я уверен, мы возьмем эти вершины!<br>
                \s""";
    }
}
