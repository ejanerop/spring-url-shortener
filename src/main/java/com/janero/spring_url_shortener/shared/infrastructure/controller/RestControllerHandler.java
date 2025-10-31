package com.janero.spring_url_shortener.shared.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.janero.spring_url_shortener.shared.domain.exception.EncodeException;
import com.janero.spring_url_shortener.shared.domain.exception.KeyNotFoundException;
import com.janero.spring_url_shortener.shared.infrastructure.controller.response.ErrorResponse;

@RestControllerAdvice
public class RestControllerHandler {

    @ExceptionHandler(EncodeException.class)
    public ResponseEntity<ErrorResponse> handleAlgorithmException(EncodeException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMsg(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(response);
    }

    @ExceptionHandler(KeyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleKeyNotFoundException(KeyNotFoundException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMsg(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
