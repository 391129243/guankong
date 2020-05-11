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

    public static int convertToInt(Object value, int defaultValue){
        if(value == null || "".equals(value.toString())){
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.toString());

        } catch (NumberFormatException e1){
            return defaultValue;
        }catch (Exception e) {
            // TODO: handle exception
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (NumberFormatException e2){
                return defaultValue;
            }catch (Exception e3) {
                // TODO: handle exception
                return defaultValue;
            }
        }
    }

    public static String getExperimentNum(String experimentName){
        String result = "";
        if(experimentName.contains("-")){
            result = experimentName.substring(0,experimentName.indexOf("-"));
        }
        return result;
    }
}
