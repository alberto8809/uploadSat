package org.example.satdwn.util;

import java.util.Base64;
import java.util.UUID;


public class EncryptionUtil {

    public static String encrypt(String value) {
        try {

            return Base64.getEncoder().encodeToString(value.getBytes());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encrypted);
            return new String(decodedBytes);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static String generateToken() {
        String tokenGenerado = UUID.randomUUID().toString().replace("-", "");
        return tokenGenerado;
    }
}
