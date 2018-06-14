package util;

import java.util.Base64;

public class StringEncrypt {

    public static String decryptXOR(String message) {
        return new String(Base64.getDecoder().decode(message));
    }

    public static String encryptXOR(String message) {
        return new String(Base64.getEncoder().encode(message.getBytes()));
    }

}