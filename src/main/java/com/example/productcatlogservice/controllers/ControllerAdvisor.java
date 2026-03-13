package com.example.productcatlogservice.controllers;

import com.example.productcatlogservice.exceptions.ProductAlreadyExistsException;
import com.example.productcatlogservice.exceptions.ProductNotFoundException;
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

  @ExceptionHandler({ProductAlreadyExistsException.class, ProductNotFoundException.class})
    public ResponseEntity<String> handleProductException(Exception exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
  }
}
