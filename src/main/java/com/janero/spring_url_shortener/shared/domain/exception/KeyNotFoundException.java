package com.janero.spring_url_shortener.shared.domain.exception;

public class KeyNotFoundException extends Exception {
    
    public KeyNotFoundException() {
        super();
    }

    public KeyNotFoundException(String message) {
        super(message);
    }

    public KeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
