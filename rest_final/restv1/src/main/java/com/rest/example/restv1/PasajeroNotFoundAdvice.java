package com.rest.example.restv1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class PasajeroNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PasajeroNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String PasajeroNotFoundHandler(PasajeroNotFoundException ex) {
        return ex.getMessage();
    }
}