package com.atmo.atmo.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * 一些常用的转换工具类
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by brioa on 2018/10/23.
 */

public class ConvertUtil {




    // 转换需要的参数到HashMap
    public static HashMap<String, Object> returnNeedValue(Object object, String[] methods) {
        if (object == null) {
            return new HashMap<>();
        }
        HashMap<String, Object> map = null;
        try {
            Class<?> userClass = Class.forName(object.getClass().getName());
            map = new HashMap<>();
            // 循环参数
            for (int i = 0; i < methods.length; i++) {
                String methodName = methods[i];
                String getName = methodName;
                // 首字母大写
                getName = (new StringBuilder()).append(Character.toUpperCase(getName.charAt(0))).append(getName.substring(1)).toString();
                // 获取get方法
                try {
                    Method method = userClass.getMethod("get" + getName);//得到方法对象
                    Object value = method.invoke(object);
                    map.put(methodName, value);
                } catch (Exception e) {
                }
                // 获取is 方法
                try {
                    Method method = userClass.getMethod("is" + getName);//得到方法对象
                    Object value = method.invoke(object);
                    map.put(methodName, value);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 返回一个查看所有页面的分页参数
     *
     * @return
     */
    public static Pageable getFullPageRequest() {
        return PageRequest.of(0, Integer.MAX_VALUE);
    }

}