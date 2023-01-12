package com.carassistant.helpers;

public class PhoneHelper {

    public static boolean regexPhoneValidationPattern(String tel) {

        String regex = "^\\d{10}$";

        if(tel.matches(regex)) {
            return true;
        }
        return false;
    }

}
