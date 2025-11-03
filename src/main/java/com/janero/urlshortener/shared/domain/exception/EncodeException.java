package com.janero.urlshortener.shared.domain.exception;

public class EncodeException extends Exception {

    public EncodeException() {
        super();
    }

    public EncodeException(String message) {
        super(message);
    }

    public EncodeException(String message, Throwable cause) {
        super(message, cause);
    }

}
