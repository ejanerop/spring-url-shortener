
package com.janero.urlshortener.shared.domain.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class EncodeExceptionTest {

    @Test
    void defaultConstructor_shouldHaveNullMessageAndNoCause() {
        EncodeException ex = new EncodeException();
        assertNull(ex.getMessage(), "Default constructor should produce null message");
        assertNull(ex.getCause(), "Default constructor should produce no cause");
    }

    @Test
    void messageConstructor_shouldStoreMessage() {
        String msg = "encoding failed";
        EncodeException ex = new EncodeException(msg);
        assertEquals(msg, ex.getMessage(), "Constructor with message should preserve the message");
        assertNull(ex.getCause(), "Constructor with only message should have no cause");
    }

    @Test
    void messageAndCauseConstructor_shouldStoreBoth() {
        String msg = "encoding failed";
        Throwable cause = new IllegalArgumentException("bad input");
        EncodeException ex = new EncodeException(msg, cause);
        assertEquals(msg, ex.getMessage(), "Constructor with message and cause should preserve the message");
        assertSame(cause, ex.getCause(), "Constructor should preserve the cause instance");
    }
}