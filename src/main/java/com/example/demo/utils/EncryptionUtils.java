package com.example.demo.utils;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionUtils {

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(15));
    }

    public static boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}
