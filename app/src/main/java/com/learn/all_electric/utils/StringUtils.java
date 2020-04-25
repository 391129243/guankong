package com.learn.all_electric.utils;

public class StringUtils {
    public static boolean isEmpty(String input){
        if (input == null || input.isEmpty()){
            return true;
        }
        return false;
    }

    public static String[] splitArray(String input){
        String[] splitArray  = input.split("\\/");
        return splitArray;
    }
}
