package com.github.birulazena.user_service.utils;

public class CardInfoUtils {

    public static String hideTheNumber(String number) {
        if (number == null || number.length() <= 4) {
            return number; // или вернуть "****"
        }
        return "*".repeat(number.length() - 4) + number.substring(number.length() - 4);
    }
}
