package com.bharani.movie.exceptions;

import jakarta.servlet.annotation.HttpConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> exception(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String,String> map = new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error->{
            map.put(error.getField(),error.getDefaultMessage());

        });

        return map;
    }

    @ExceptionHandler(FileAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> FileAlreadyExistsException(FileAlreadyExistsException ex){
        return Map.of("status",ex.getMessage());
    }
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> FileNotFoundException(FileNotFoundException ex){
        return Map.of("status",ex.getMessage());
    }
    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> MovieNotFoundException(MovieNotFoundException ex){
        return Map.of("status",ex.getMessage());
    }
}
