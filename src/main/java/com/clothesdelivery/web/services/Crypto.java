package com.clothesdelivery.web.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Crypto {
    private static final BCryptPasswordEncoder _encoder = new BCryptPasswordEncoder();

    public static String apply(String text) {
        return _encoder.encode(text);
    }

    public static boolean compare(String encriptedText, String nonEncriptedText) {
        return _encoder.matches(nonEncriptedText, encriptedText);
    }
}
