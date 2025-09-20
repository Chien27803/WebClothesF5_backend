package com.javanc.ultis;

import com.javanc.controlleradvice.customeException.AppException;

public class NumberUltis {
    public static boolean checkNumber(String value) {
        try {
            Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static Long parseLong(String value) throws NumberFormatException {
        return Long.parseLong(value);
    }
}
