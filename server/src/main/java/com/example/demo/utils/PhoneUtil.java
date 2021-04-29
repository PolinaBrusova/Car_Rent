package com.example.demo.utils;

/**
 * Util for validation phone numbers
 */
public class PhoneUtil {

    /**
     * Validates phone number via regex
     * @param phone String vakue of the phone number
     * @return boolean result of validation
     */
    public static boolean validPhone(String phone){
        return phone.matches("^\\+\\d?-?\\d?\\d?\\d{11}$");
        //Валидация номера телефона позволяет отсеить поиск по формату +(кодСтраны)(11цифр) без скобок и тире
    }
}

