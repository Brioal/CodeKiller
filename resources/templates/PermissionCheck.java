package com.atmo.atmo.injections;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by brioa on 2018/10/16.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCheck {

    // 是否允许管理员使用
    boolean ADMIN_ALLOW() default false;

    // 是否允许企业
    boolean COMPANY_ALLOW() default false;

    // 允许乡镇企业
    boolean COMPANY_TOWN_ALLOW() default false;

}
