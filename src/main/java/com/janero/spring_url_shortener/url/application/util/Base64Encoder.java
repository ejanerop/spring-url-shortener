package com.janero.spring_url_shortener.url.application.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import com.janero.spring_url_shortener.shared.domain.exception.EncodeException;

public class Base64Encoder {

    private Base64Encoder() {}

    private static Random random = new Random();

    public static String encode(String url) throws EncodeException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(url.getBytes(StandardCharsets.UTF_8));

            String base64 = Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);

            String hash = base64.substring(0, 8);
            String salt = generateRandomSalt();
            return hash.concat(salt);

        } catch (NoSuchAlgorithmException e) {
            throw new EncodeException("Error generating hash", e);
        }
    }

    private static String generateRandomSalt() {
        byte[] saltBytes = new byte[6];
        random.nextBytes(saltBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(saltBytes);
    }

}
