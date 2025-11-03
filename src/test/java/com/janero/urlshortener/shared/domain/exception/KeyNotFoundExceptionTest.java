package com.janero.urlshortener.shared.domain.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



class KeyNotFoundExceptionTest {

    @Test
    void testDefaultConstructor() {
        KeyNotFoundException exception = new KeyNotFoundException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithMessage() {
        String message = "Key not found";
        KeyNotFoundException exception = new KeyNotFoundException(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Key not found";
        Throwable cause = new RuntimeException("Cause");
        KeyNotFoundException exception = new KeyNotFoundException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}