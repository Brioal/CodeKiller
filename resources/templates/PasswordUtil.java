package com.atmo.atmo.util;

/**
 * 密码生成器
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by brioa on 2018/10/16.
 */
public class PasswordUtil {
    /**
     * 生成随机密码
     *
     * @return
     */
    public static String generatePassword() {
        return  RandomUtil.randomNumStr(6);
    }

}
