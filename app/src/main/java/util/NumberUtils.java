package util;

import java.text.DecimalFormat;

public class NumberUtils {

    public static DecimalFormat DOUBLE_FORMAT = new DecimalFormat("#######.00");

    public static double StringtoDouble(String string) {
        return Double.parseDouble("".equals(string) ? "0.00" : string.toString());
    }

    public static Integer StringtoInt(String string) {
        return Integer.parseInt("".equals(string) ? "0" : string.toString());
    }


}
