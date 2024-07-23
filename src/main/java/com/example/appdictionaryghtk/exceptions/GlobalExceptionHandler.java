package com.example.appdictionaryghtk.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> dataNotFoundExceptionHandling(DataNotFoundException exception){
        return ResponseEntity.ok(exception.getMessage());
    }

    @ExceptionHandler(MissingPropertyException.class)
    @ResponseStatus()
    public ResponseEntity<?> missingPropertyExceptionHandling(MissingPropertyException exception){
        return ResponseEntity.ok(exception.getMessage());
    }
}
