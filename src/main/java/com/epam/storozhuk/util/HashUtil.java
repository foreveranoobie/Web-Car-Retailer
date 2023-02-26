package com.epam.storozhuk.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    private static final Logger LOGGER = Logger.getLogger(HashUtil.class);

    public static String hashPassword(String password) {
        StringBuilder returnPassword = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passwordBytes = md.digest(password.getBytes());
            for (byte b : passwordBytes) {
                returnPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.debug("Exception: " + e.getMessage());
        }
        return returnPassword.toString();
    }

    public static String codeCaptchaAnswer(String answer) {
        if (answer == null || answer.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int m = 0; m < answer.length(); m++) {
            result.append(answer.codePointAt(m) + "-");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }
}
