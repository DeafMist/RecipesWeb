package com.github.deafmist.recipesweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST)
public class NoSuchIngredientException extends RuntimeException {
    public NoSuchIngredientException() {
    }

    public NoSuchIngredientException(String message) {
        super(message);
    }
}
