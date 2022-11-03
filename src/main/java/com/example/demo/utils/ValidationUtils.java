package com.example.demo.utils;

public class ValidationUtils {

    public static boolean isValidPassword(String password) {
        return password.matches("^[a-zA-Z\\d]{6,10}$");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^0[1-9]{9}$");
    }

    public static boolean isValidUuid(String uuid) {
        return uuid.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    }

}
