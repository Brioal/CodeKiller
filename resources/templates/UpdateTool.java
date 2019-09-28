package com.atmo.atmo.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class UpdateTool {
    /**
     * 将要设置的数据当中不为null的属性设置到从数据库读取的数据里面
     *
     * @param source 要设置的数据
     * @param target 从数据库读取的
     */
    public static void copyNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNoNullProperties(source));
    }

    /**
     * @param source 要修改的实体
     * @return 将目标源中为空的字段取出
     */
    private static String[] getNoNullProperties(Object source) {
        BeanWrapper srcBean = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            String name = p.getName();
            Object value = srcBean.getPropertyValue(name);
            if (value == null) emptyNames.add(name);
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
