package com.janero.urlshortener.shared.infrastructure.controller;

import com.janero.urlshortener.shared.domain.exception.EncodeException;
import com.janero.urlshortener.shared.domain.exception.KeyNotFoundException;
import com.janero.urlshortener.shared.infrastructure.controller.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;




class RestControllerHandlerTest {

    private final RestControllerHandler handler = new RestControllerHandler();

    @Test
    void handleAlgorithmException_returnsFailedDependency() {
        String errorMsg = "Encoding failed";
        EncodeException ex = new EncodeException(errorMsg);

        ResponseEntity<ErrorResponse> response = handler.handleAlgorithmException(ex);

        assertEquals(HttpStatus.FAILED_DEPENDENCY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMsg, response.getBody().getMsg());
    }

    @Test
    void handleKeyNotFoundException_returnsNotFound() {
        String errorMsg = "Key not found";
        KeyNotFoundException ex = new KeyNotFoundException(errorMsg);

        ResponseEntity<ErrorResponse> response = handler.handleKeyNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMsg, response.getBody().getMsg());
    }
}