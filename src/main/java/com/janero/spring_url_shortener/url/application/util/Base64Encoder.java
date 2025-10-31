package com.janero.spring_url_shortener.url.application.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import com.janero.spring_url_shortener.shared.domain.exception.EncodeException;

public class Base64Encoder {

    private Base64Encoder() {}

    public static String encode(String url) throws EncodeException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(url.getBytes(StandardCharsets.UTF_8));

            String base64 = Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);

            return base64.substring(0, 8);

        } catch (NoSuchAlgorithmException e) {
            throw new EncodeException("Error generating hash", e);
        }
    }

}
