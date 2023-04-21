package com.easy.imc.webserviceeasyimc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleAllException {
    @ExceptionHandler
    public ResponseEntity<CustomError> handle(Exception ex){
        CustomError error = new CustomError();
        error.message = ex.getMessage();
        error.status = HttpStatus.BAD_REQUEST.value();
        error.timestamps = System.currentTimeMillis();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
}
