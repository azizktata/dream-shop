package com.example.dreamshops.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ApiErrorResponse implements Serializable {
    private int status;
    private String message;
    private String exception;

    public ApiErrorResponse(int status, String message, String exception) {
        this.status = status;
        this.message = message;
        this.exception = exception;
    }
}
