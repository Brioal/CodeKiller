package com.atmo.atmo.util;

/**
 * Integer工具类
 */
public class IntegerUtil {


    public static int parseInt(String string) {
        if (TextUtil.isNotValid(string)) {
            return 0;
        }
        int result = 0;
        try {
            result = Integer.parseInt(string);
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
    }


    /**
     * 是否可用
     *
     * @param integer
     * @return
     */
    public static boolean isValid(Integer integer) {
        if (integer == null) {
            return false;
        }
        return true;
    }

    /**
     * 是否不可用
     *
     * @param integer
     * @return
     */
    public static boolean isNotValid(Integer integer) {
        return !isValid(integer);
    }
}
