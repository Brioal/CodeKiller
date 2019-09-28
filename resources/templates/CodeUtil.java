package com.atmo.atmo.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 用于生成编号相关的工具
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by brioa on 2019/2/8.
 */

public class CodeUtil {

    Logger mLogger = LoggerFactory.getLogger(CodeUtil.class);


    /**
     * 生成编号
     *
     * @param front 前缀
     * @param id    后缀 带id的三位
     * @return
     */
    public static String generateCode(int front, int id) {
        return generateCode(front, id, false);
    }

    /**
     * 生成编号
     *
     * @param front 前缀
     * @param id    后缀 带id的三位
     * @return
     */
    public static String generateCode(int front, int id, boolean year) {
        // 格式化id 不到5位则补0
        int offset = 5 - (id + "").length();
        StringBuffer codeBuffer = new StringBuffer();
        if (year) {
            codeBuffer.append(new Date().getYear());
        }
        if (offset > 0) {
            for (int i = 0; i < offset; i++) {
                codeBuffer.append("0");
            }
        }
        codeBuffer.append(id);
        if (front == -1) {
            return codeBuffer.toString();
        }
        return front + codeBuffer.toString();
    }

    /**
     * int数字拼接成字符串,四位,前面补0
     *
     * @param id
     * @return
     */
    public static String appendZeroToFourSize(int id) {
        int offset = 3 - (id + "").length();
        StringBuffer codeBuffer = new StringBuffer();
        if (offset > 0) {
            for (int i = 0; i < offset; i++) {
                codeBuffer.append("0");
            }
        }
        codeBuffer.append(id);
        return codeBuffer.toString();
    }


    /**
     * 促销编号生成规则 PRO201906091452001
     *
     * @param id
     * @return
     */
    public static String generateCodeForPromotion(int id) {
        String timeStr = ReviewDateFormatUtl.formatDate(new Timestamp(System.currentTimeMillis()), "yyyyMMddHHmmss");
        return "PRO" + timeStr + appendZeroToFourSize(id);
    }

    /**
     * 联合采购编号生成规则 PRO201906091452001
     *
     * @param id
     * @return
     */
    public static String generateCodeForJoint(int id) {
        String timeStr = ReviewDateFormatUtl.formatDate(new Timestamp(System.currentTimeMillis()), "yyyyMMddHHmmss");
        return "JOIN" + timeStr + appendZeroToFourSize(id);
    }


}
