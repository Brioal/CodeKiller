package com.atmo.atmo.config;


import com.atmo.atmo.bean.company.CompanyTownBean;
import com.atmo.atmo.bean.user.AdminUserBean;
import com.atmo.atmo.bean.user.CompanyUserBean;
import com.atmo.atmo.injections.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 当前用户自动注入
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by brioa on 2018/10/16.
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.getParameterType().isAssignableFrom(CompanyUserBean.class)) || (parameter.getParameterType().isAssignableFrom(AdminUserBean.class) || (parameter.getParameterType().isAssignableFrom(CompanyTownBean.class)));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object user = webRequest.getAttribute(CurrentUser.CURRENT_USER_CODE, RequestAttributes.SCOPE_REQUEST);
        if (user != null) {
            return user;
        }
        throw new MissingServletRequestPartException(CurrentUser.CURRENT_USER_CODE);
    }
}
