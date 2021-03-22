package com.example.demo.utils;

public class PhoneUtil {
    public static boolean validPhone(String phone){
        return phone.matches("^\\+\\d?-?\\d?\\d?\\d{11}$");
        //Валидация номера телефона позволяет отсеить поиск по формату +(кодСтраны)(11цифр) без скобок и тире
    }
}

