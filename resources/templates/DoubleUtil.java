package com.atmo.atmo.util;

import java.text.DecimalFormat;

/**
 * Integer工具类
 */
public class DoubleUtil {

    /**
     * 格式化小数
     *
     * @param d
     * @return
     */
    public static String formatDouble2(double d) {
        if (d == 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }

    public static String formatDouble4(double d) {
        if (d == 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(d);
    }

    public static double parseDouble(String string) {
        if (TextUtil.isNotValid(string)) {
            return 0;
        }
        double result = 0;
        try {
            result = Double.parseDouble(string);
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
    }

    /**
     * 获取可用于的值
     *
     * @param d
     * @return
     */
    public static double getValue(Double d) {
        if (d == null) {
            return -1;
        }
        return d;
    }

}
