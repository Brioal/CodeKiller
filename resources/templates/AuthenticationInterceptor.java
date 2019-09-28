package com.atmo.atmo.config;

import com.atmo.atmo.bean.company.CompanyTownBean;
import com.atmo.atmo.bean.tool.ResultBean;
import com.atmo.atmo.bean.user.AdminUserBean;
import com.atmo.atmo.bean.user.CompanyUserBean;
import com.atmo.atmo.injections.CurrentUser;
import com.atmo.atmo.injections.PermissionCheck;
import com.atmo.atmo.service.CompanyUserService;
import com.atmo.atmo.service.company.CompanyTownService;
import com.atmo.atmo.service.user.AdminUserService;
import com.atmo.atmo.util.TextUtil;
import com.atmo.atmo.util.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by brioa on 2018/10/16.
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    public final static String ACCESS_TOKEN = "token";

    private CompanyUserService companyUserService;
    private AdminUserService adminUserService;
    private CompanyTownService companyTownService;


    @Autowired
    public void setCompanyTownService(CompanyTownService companyTownService) {
        this.companyTownService = companyTownService;
    }

    @Autowired
    public void setAdminUserService(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @Autowired
    public void setCompanyUserService(CompanyUserService companyUserService) {
        this.companyUserService = companyUserService;
    }


    /**
     * 数据写入response
     *
     * @param response
     * @param responseObject
     */
    protected void responseOutWithJson(HttpServletResponse response,
                                       Object responseObject) {
        //将实体对象转换为JSON Object转换
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            String text = mapper.writeValueAsString(responseObject);
            out = response.getWriter();
            out.append(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在请求处理之前进行调用(Controller方法调用之前)
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        PermissionCheck methodAnnotation = method.getAnnotation(PermissionCheck.class);
        if (methodAnnotation != null) {
            // 判断是否存在令牌信息，如果存在，则允许登录
            String accessToken = request.getHeader(ACCESS_TOKEN);
            if (!TextUtil.isStringAvailableAddNotNull(accessToken)) {
                responseOutWithJson(response, ResultBean.returnLogin());
                return false;
            }
            Claims claims = TokenUtils.parseJWT(accessToken);
            if (claims == null) {
                responseOutWithJson(response, ResultBean.returnRLogin());
                return false;
            }
            Date expireDate = claims.getExpiration();
            if (expireDate.before(new Date(System.currentTimeMillis()))) {
                responseOutWithJson(response, ResultBean.returnRLogin());
                return false;
            }
            // 获取id
            String code = claims.getId();
            // 获取企业用户
            CompanyUserBean companyUserBean = companyUserService.findByCodeOrName(code);
            // 获取管理员用户
            AdminUserBean adminUserBean = adminUserService.findByCode(code);
            // 获取企业乡镇用户
            CompanyTownBean companyTownBean = companyTownService.getByCode(code);
            // 判断是否可以访问
            if ((methodAnnotation.COMPANY_ALLOW() && companyUserBean != null) || (methodAnnotation.ADMIN_ALLOW() && adminUserBean != null) || (methodAnnotation.COMPANY_TOWN_ALLOW() && companyTownBean != null)) {
                // 可以访问
                // 当前登录用户@CurrentUser
                if (companyUserBean != null) {
                    request.setAttribute(CurrentUser.CURRENT_USER_CODE, companyUserBean);
                } else if (adminUserBean != null) {
                    request.setAttribute(CurrentUser.CURRENT_USER_CODE, adminUserBean);
                } else if (companyTownBean != null) {
                    request.setAttribute(CurrentUser.CURRENT_USER_CODE, companyTownBean);
                }
            } else {
                // 没有权限
                responseOutWithJson(response, ResultBean.returnNotAllow());
                return false;
            }
        }
        return true;
    }
}
