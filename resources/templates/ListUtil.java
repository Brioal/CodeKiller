package com.atmo.atmo.util;

import java.util.List;


/**
 * List的一些判断
 *
 * @author brioa
 */
public class ListUtil {


    /**
     * 判断一个List是否可用
     *
     * @param list
     * @return
     */
    public static boolean isAvaliable(List list) {
        if (list == null) {
            return false;
        }
        if (list.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断一个List是否可用
     *
     * @param list
     * @return
     */
    public static boolean isValid(List list) {
        if (list == null) {
            return false;
        }
        if (list.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断一个List是否不可用
     *
     * @param list
     * @return
     */
    public static boolean isNotValid(List list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        return false;
    }
}
