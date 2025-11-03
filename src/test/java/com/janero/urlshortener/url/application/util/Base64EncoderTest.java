package com.janero.urlshortener.url.application.util;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class Base64EncoderTest {

    private static String invokeGenerateRandomSalt() throws Exception {
        Method m = Base64Encoder.class.getDeclaredMethod("generateRandomSalt");
        m.setAccessible(true);
        return (String) m.invoke(null);
    }

    @Test
    void generateRandomSalt_producesValidBase64UrlString_andDecodesTo6Bytes() throws Exception {
        String salt = invokeGenerateRandomSalt();

        assertNotNull(salt, "salt should not be null");
        assertEquals(8, salt.length(), "expected 8 characters for 6 bytes base64-url without padding");
        assertTrue(salt.matches("[A-Za-z0-9_-]+"), "salt contains only base64-url characters");

        byte[] decoded = Base64.getUrlDecoder().decode(salt);
        assertEquals(6, decoded.length, "decoded salt should be 6 bytes");
    }

    @Test
    void generateRandomSalt_multipleInvocationsProduceUniqueValues() throws Exception {
        final int iterations = 100;
        Set<String> salts = new HashSet<>(iterations);

        for (int i = 0; i < iterations; i++) {
            salts.add(invokeGenerateRandomSalt());
        }

        assertEquals(iterations, salts.size(), "expected all generated salts to be unique across iterations");
    }
    
}