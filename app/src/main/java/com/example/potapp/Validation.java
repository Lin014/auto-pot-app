package com.example.potapp;

public class Validation {

    public static boolean isEmpty(String str) {
        return str.isEmpty();
    }

    public static boolean isLengthValid(String str) {
        return str.length() <= 30;
    }

    public static boolean isFirsCharAlpha(String str) {
        String s = str.substring(0, 1);
        return s.matches("\\p{Alpha}");
    }

    public static boolean isCharValid(String str) {
        String s;
        for (int i = 0; i < str.length(); i++) {
            s = str.substring(i, i+1);
            if (!s.matches("\\p{Alnum}|[-]|[_]|[']|[.]")) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkTime(int time) {
        if (time < 13 && time > 0) {
            return true;
        } else {
            return false;
        }
    }
}
