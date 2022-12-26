package com.github.deafmist.recipesweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST)
public class NoSuchRecipeException extends RuntimeException {
    public NoSuchRecipeException() {
    }

    public NoSuchRecipeException(String message) {
        super(message);
    }
}
