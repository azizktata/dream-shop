package com.example.dreamshops.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ApiErrorResponse handleNotFound(Exception e) {
        return new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), e.getClass().getName());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({AlreadyExistsException.class})
    public ApiErrorResponse handleAlreadyExists(Exception e) {
        return new ApiErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(), e.getClass().getName());
    }


}
