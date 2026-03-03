package com.example.productcatlogservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

//  Multiple exception classes are passed in a { ex1, ex2, ... }
  @ExceptionHandler({IllegalArgumentException.class, ArrayIndexOutOfBoundsException.class})
  public ResponseEntity<String> handleExceptions(Exception exception) {
    return new  ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
